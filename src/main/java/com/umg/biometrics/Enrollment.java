package com.umg.biometrics;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.digitalpersona.uareu.*;

public class Enrollment extends JPanel implements ActionListener {

    public class EnrollmentThread extends Thread implements Engine.EnrollmentCallback {
        public static final String ACT_PROMPT = "enrollment_prompt";
        public static final String ACT_CAPTURE = "enrollment_capture";
        public static final String ACT_FEATURES = "enrollment_features";
        public static final String ACT_DONE = "enrollment_done";
        public static final String ACT_CANCELED = "enrollment_canceled";

        public class EnrollmentEvent extends ActionEvent {
            private static final long serialVersionUID = 102;
            public Reader.CaptureResult capture_result;
            public Reader.Status reader_status;
            public UareUException exception;
            public Fmd enrollment_fmd;

            public EnrollmentEvent(Object source, String action, Fmd fmd, Reader.CaptureResult cr, Reader.Status st, UareUException ex) {
                super(source, ActionEvent.ACTION_PERFORMED, action);
                capture_result = cr;
                reader_status = st;
                exception = ex;
                enrollment_fmd = fmd;
            }
        }

        private final Reader m_reader;
        private CaptureThread m_capture;
        private ActionListener m_listener;
        private boolean m_bCancel;

        protected EnrollmentThread(Reader reader, ActionListener listener) {
            m_reader = reader;
            m_listener = listener;
        }

        public Engine.PreEnrollmentFmd GetFmd(Fmd.Format format) {
            Engine.PreEnrollmentFmd prefmd = null;

            while (null == prefmd && !m_bCancel) {
                m_capture = new CaptureThread(m_reader, false, Fid.Format.ANSI_381_2004, Reader.ImageProcessing.IMG_PROC_DEFAULT);
                m_capture.start(null);

                SendToListener(ACT_PROMPT, null, null, null, null);
                m_capture.join(0);

                CaptureThread.CaptureEvent evt = m_capture.getLastCaptureEvent();
                if (null != evt.capture_result) {
                    if (Reader.CaptureQuality.CANCELED == evt.capture_result.quality) {
                        break;
                    } else if (null != evt.capture_result.image && Reader.CaptureQuality.GOOD == evt.capture_result.quality) {
                        Engine engine = UareUGlobal.GetEngine();
                        try {
                            Fmd fmd = engine.CreateFmd(evt.capture_result.image, Fmd.Format.ANSI_378_2004);
                            prefmd = new Engine.PreEnrollmentFmd();
                            prefmd.fmd = fmd;
                            prefmd.view_index = 0;
                            SendToListener(ACT_FEATURES, null, null, null, null);
                        } catch (UareUException e) {
                            SendToListener(ACT_FEATURES, null, null, null, e);
                        }
                    } else {
                        SendToListener(ACT_CAPTURE, null, evt.capture_result, evt.reader_status, evt.exception);
                    }
                } else {
                    SendToListener(ACT_CAPTURE, null, evt.capture_result, evt.reader_status, evt.exception);
                }
            }

            return prefmd;
        }

        public void cancel() {
            m_bCancel = true;
            if (null != m_capture) m_capture.cancel();
        }

        private void SendToListener(String action, Fmd fmd, Reader.CaptureResult cr, Reader.Status st, UareUException ex) {
            if (null == m_listener || null == action || action.equals("")) return;

            final EnrollmentEvent evt = new EnrollmentEvent(this, action, fmd, cr, st, ex);

            try {
                javax.swing.SwingUtilities.invokeAndWait(() -> m_listener.actionPerformed(evt));
            } catch (InvocationTargetException | InterruptedException e) {
                e.printStackTrace();
            }
        }

        public void run() {
            Engine engine = UareUGlobal.GetEngine();
            try {
                m_bCancel = false;
                while (!m_bCancel) {
                    Fmd fmd = engine.CreateEnrollmentFmd(Fmd.Format.ANSI_378_2004, this);
                    if (null != fmd) {
                        SendToListener(ACT_DONE, fmd, null, null, null);
                    } else {
                        SendToListener(ACT_CANCELED, null, null, null, null);
                        break;
                    }
                }
            } catch (UareUException e) {
                SendToListener(ACT_DONE, null, null, null, e);
            }
        }
    }

    private static final long serialVersionUID = 6;
    private static final String ACT_BACK = "back";

    private EnrollmentThread m_enrollment;
    private Reader m_reader;
    private JDialog m_dlgParent;
    private JTextArea m_text;
    private boolean m_bJustStarted;
    private Fmd plantillaCapturada;

    private Enrollment(Reader reader) {
        m_reader = reader;
        m_bJustStarted = true;
        m_enrollment = new EnrollmentThread(m_reader, this);

        final int vgap = 5;
        final int width = 380;

        BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(layout);

        m_text = new JTextArea(22, 1);
        m_text.setEditable(false);
        JScrollPane paneReader = new JScrollPane(m_text);
        add(paneReader);
        Dimension dm = paneReader.getPreferredSize();
        dm.width = width;
        paneReader.setPreferredSize(dm);

        add(Box.createVerticalStrut(vgap));

        JButton btnBack = new JButton("Back");
        btnBack.setActionCommand(ACT_BACK);
        btnBack.addActionListener(this);
        add(btnBack);
        add(Box.createVerticalStrut(vgap));

        setOpaque(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(ACT_BACK)) {
            m_dlgParent.setVisible(false);
        } else {
            EnrollmentThread.EnrollmentEvent evt = (EnrollmentThread.EnrollmentEvent) e;

            if (e.getActionCommand().equals(EnrollmentThread.ACT_PROMPT)) {
                if (m_bJustStarted) {
                    m_text.append("Inicio del registro\n");
                    m_text.append("    Coloca cualquier dedo en el lector\n");
                } else {
                    m_text.append("    Coloca el mismo dedo en el lector\n");
                }
                m_bJustStarted = false;
            } else if (e.getActionCommand().equals(EnrollmentThread.ACT_CAPTURE)) {
                if (null != evt.capture_result) {
                    System.out.println(evt.capture_result.quality);
                } else if (null != evt.exception) {
                    System.out.println(evt.exception);
                } else if (null != evt.reader_status) {
                    System.out.println(evt.reader_status);
                }
                m_bJustStarted = false;
            } else if (e.getActionCommand().equals(EnrollmentThread.ACT_FEATURES)) {
                if (null == evt.exception) {
                    m_text.append("    huella capturada correctamente\n\n");
                } else {
                    System.out.println(evt.exception);
                }
                m_bJustStarted = false;
            } else if (e.getActionCommand().equals(EnrollmentThread.ACT_DONE)) {
                if (null == evt.exception) {
                    String str = String.format("    plantilla de registro creada, tamaño: %d\n\n\n", evt.enrollment_fmd.getData().length);
                    m_text.append(str);

                    plantillaCapturada = evt.enrollment_fmd;
                    m_dlgParent.setVisible(false);
                } else {
                    System.out.println(evt.exception);
                }
                m_bJustStarted = true;
            } else if (e.getActionCommand().equals(EnrollmentThread.ACT_CANCELED)) {
                m_dlgParent.setVisible(false);
            }

            if (null != evt.exception) {
                m_dlgParent.setVisible(false);
            } else if (null != evt.reader_status && Reader.ReaderStatus.READY != evt.reader_status.status && Reader.ReaderStatus.NEED_CALIBRATION != evt.reader_status.status) {
                m_dlgParent.setVisible(false);
            }
        }
    }

    private void doModal(JDialog dlgParent) {
        try {
            m_reader.Open(Reader.Priority.COOPERATIVE);
        } catch (UareUException e) {
            System.out.println("error: " + e.getMessage());
        }

        m_enrollment.start();
        m_dlgParent = dlgParent;
        m_dlgParent.setContentPane(this);
        m_dlgParent.pack();
        m_dlgParent.setLocationRelativeTo(null);
        m_dlgParent.setVisible(true);
        m_dlgParent.dispose();

        m_enrollment.cancel();

        try {
            m_reader.Close();
        } catch (UareUException e) {
            System.out.println("error: " + e.getMessage());
        }
    }

    public static Fmd Run(Reader reader) {
        JDialog dlg = new JDialog((JDialog) null, "Captura de huella", true);
        Enrollment enrollment = new Enrollment(reader);
        enrollment.doModal(dlg);
        return enrollment.plantillaCapturada;
    }
}

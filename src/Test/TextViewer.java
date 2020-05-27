package Test;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.DefaultEditorKit;

public class TextViewer {

    public static void main(final java.lang.String[] args) {
        java.awt.EventQueue.invokeLater(new java.lang.Runnable(){
            @Override public void run(){
                final JFrame frame = new JFrame("Text Viewer");
                final JButton load = new JButton("Load ...");
                final JLabel filename = new JLabel("");
                final JTextArea textarea = new JTextArea(25, 80);
                textarea.setLineWrap(true);
                textarea.setWrapStyleWord(true);
                final JScrollPane scroller = new JScrollPane(textarea);
                load.addActionListener(new ActionListener(){
                    private JFileChooser filechooser = null;
                    private DefaultEditorKit kit = new DefaultEditorKit();
                    @Override public void actionPerformed(ActionEvent e){
                        if (filechooser == null) {
                            filechooser = new JFileChooser(System.getProperty("user.home"));
                        }
                        filechooser.setFileFilter(new FileNameExtensionFilter("Text Files","txt","text","java"));
                        if (filechooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
                            filename.setText(filechooser.getSelectedFile().getAbsolutePath());
                            FileReader reader = null;
                            try {
                                reader = new FileReader(filechooser.getSelectedFile());
                                textarea.setText("");
                                kit.read(reader,textarea.getDocument(),0);
                            } catch (Exception xe) {
                                System.err.println(xe.getMessage());
                            } finally {
                                if (reader != null) {
                                    try {
                                        reader.close();
                                    } catch (IOException ioe) {
                                        System.err.println(ioe.getMessage());
                                    }
                                }
                            }
                            textarea.setCaretPosition(0);
                        }
                        return;
                    }
                });

                final Box top = Box.createHorizontalBox();
                top.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
                top.add(load);
                top.add(Box.createHorizontalStrut(5));
                top.add(filename);
                frame.add(top,BorderLayout.PAGE_START);
                frame.add(scroller);
                frame.pack();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }
}
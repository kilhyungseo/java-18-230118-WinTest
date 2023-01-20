import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JComboBox;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.DefaultComboBoxModel;

public class WinAnimal extends JDialog {
   private JLabel lblPic;
   private JComboBox cbYear;

   /**
    * Launch the application.
    */
   public static void main(String[] args) {
      EventQueue.invokeLater(new Runnable() {
         public void run() {
            try {
               WinAnimal dialog = new WinAnimal();
               dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
               dialog.setVisible(true);
            } catch (Exception e) {
               e.printStackTrace();
            }
         }
      });
   }

   /**
    * Create the dialog.
    */
   public WinAnimal() {
      setTitle("년도 => 동물 보이기");
      setBounds(100, 100, 352, 363);
      getContentPane().setLayout(null);
      
      JLabel lblYear = new JLabel("연도:");
      lblYear.setBounds(12, 14, 57, 15);
      getContentPane().add(lblYear);
      
      JButton btnRun = new JButton("실행");
      btnRun.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            showAnimal();
         }
      });
      btnRun.setBounds(226, 10, 97, 23);
      getContentPane().add(btnRun);
      
      lblPic = new JLabel("");
      lblPic.setBounds(61, 64, 200, 250);
      getContentPane().add(lblPic);
      
      cbYear = new JComboBox();
      cbYear.setEditable(true);
      cbYear.addItemListener(new ItemListener() {
         public void itemStateChanged(ItemEvent e) {
            showAnimal();
         }
      });
      cbYear.setBounds(61, 10, 134, 23);
      getContentPane().add(cbYear);
      cbYear.setSelectedItem(1985);
      
      
      for(int year=1;year<=3000;year++)
         cbYear.addItem(year);
   }

   protected void showAnimal() {
      String Animal[]= {"원숭이","닭","개","돼지","쥐","소","호랑이","토끼","용","뱀","말","양"};
      String twelve[]= {"신","유","술","해","자","축","인","묘","진","사","오","미"};
      
      int year =  (int)cbYear.getSelectedItem() ;
      System.out.println(Animal[year%12]);
      
      ImageIcon img = new ImageIcon("./images/" + Animal[year%12] + ".png");
      Image image = img.getImage();
      image = image.getScaledInstance(200, 250, Image.SCALE_SMOOTH);
      ImageIcon pic = new ImageIcon(image);
      
      lblPic.setIcon(pic);
   }
}
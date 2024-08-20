
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author HP
 */
public class NewJFrame_quiz extends javax.swing.JFrame {

    /**
     * Creates new form NewJFrame_quiz
     * 
     */

class clock extends Thread {
    @Override
    public void run() {
        while (true) {
            Calendar c = Calendar.getInstance();
            int h = c.get(Calendar.HOUR); 
            if (h == 0) h = 12; 
            int m = c.get(Calendar.MINUTE);
            int s = c.get(Calendar.SECOND);
            String am_pm = c.get(Calendar.AM_PM) == Calendar.AM ? "AM" : "PM";
            jLabel1.setText(String.format("%02d:%02d:%02d %s", h, m, s, am_pm));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(NewJFrame_quiz.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
        class Timer extends Thread {
    @Override
    public void run() {
        int minutes = 10;
        int seconds = 0;

        while (minutes > 0 || seconds > 0) {
            try {
                jLabel3.setText(String.format("%02d:%02d", minutes, seconds));
                Thread.sleep(1000);

                if (seconds == 0) {
                    minutes--;
                    seconds = 59;
                } else {
                    seconds--;
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(NewJFrame_quiz.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        // Time is up
        jLabel3.setText("00:00");
        jLabel1_question.setText("Time's up! Your score is " + correct + "/" + questions.length + ".");
        System.out.println("Time's up!");

        // Disable answer buttons and next button
        enableRbuttons(false);
        jButton1_nextQ.setText("Restart");
        jButton1_nextQ.setEnabled(true);
        jButton1_previousQ.setEnabled(false);
    }
}

        private String[] readQuestionsFromCSV(String filePath) {
    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
        return br.lines().toArray(String[]::new);
    } catch (IOException e) {
        e.printStackTrace();
        return new String[0];
    }
}

private String[][] readOptionsFromCSV(String filePath) {
    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
        return br.lines().map(line -> line.split(",")).toArray(String[][]::new);
    } catch (IOException e) {
        e.printStackTrace();
        return new String[0][0];
    }
}
      String[] questions = {};
      String[][] options = {};
      int[] userSelections;
      private String userName;
      private int tries;
      private int maxScore;
      
    public NewJFrame_quiz() {
        this.userName = "Guest"; // Or handle it as per your requirement
        initComponents(); // Initialize GUI components
        
        // Display the user name on the quiz screen
        userNameLabel.setText("Welcome, " + userName);
        
        // Load questions and options from CSV files
        questions = readQuestionsFromCSV("src/questions.csv");
        options = readOptionsFromCSV("src/options.csv");
        
        // Initialize user selections
        userSelections = new int[questions.length];
        for (int i = 0; i < userSelections.length; i++) {
            userSelections[i] = -1;
        }
        
        // Add radio buttons to the ButtonGroup
        bg.add(jRadioButton1);
        bg.add(jRadioButton2);
        bg.add(jRadioButton3);
        bg.add(jRadioButton4);

        // Start clock and timer threads
        clock c = new clock();
        c.start();
        Timer t = new Timer();
        t.start();

        // Display the first question
        jButton1_nextQActionPerformed(null);
    }

    
    public NewJFrame_quiz(String name, int tries, int maxScore) {
        this.userName = name;
        this.tries = tries;
        this.maxScore = maxScore;
        initComponents();
        // Display the user name, tries, and max score on the quiz screen
        userNameLabel.setText("Welcome, " + userName + ", Tries: " + tries + "/5, Max Score: " + maxScore);
        
        // Initialize other components
        questions = readQuestionsFromCSV("src/questions.csv");
        options = readOptionsFromCSV("src/options.csv");
        userSelections = new int[questions.length];
        for (int i = 0; i < userSelections.length; i++) {
            userSelections[i] = -1;
        }
        bg.add(jRadioButton1);
        bg.add(jRadioButton2);
        bg.add(jRadioButton3);
        bg.add(jRadioButton4);
        clock c = new clock();
        c.start();
        Timer t = new Timer();
        t.start();
        jButton1_nextQActionPerformed(null);
    
    }

    public void getSelectedOption(JRadioButton rbtn)
    {
          int selectedOption = -1;
          if (jRadioButton1.isSelected()) selectedOption = 0;
          if (jRadioButton2.isSelected()) selectedOption = 1;
          if (jRadioButton3.isSelected()) selectedOption = 2;
          if (jRadioButton4.isSelected()) selectedOption = 3;

          userSelections[index] = selectedOption;

          int correctOption = Integer.parseInt(options[index][4]); 

          // Debug output
          System.out.println("Selected Option: " + selectedOption);
          System.out.println("Correct Option: " + correctOption);

          // Compare integers directly
          if (selectedOption == correctOption) 
          {
              correct++;
          }
          index++;
          enableRbuttons(false);
    }
    public void enableRbuttons(boolean yes_or_no)
    {
        jRadioButton1.setEnabled(yes_or_no);
        jRadioButton2.setEnabled(yes_or_no);
        jRadioButton3.setEnabled(yes_or_no);
        jRadioButton4.setEnabled(yes_or_no);
        
    }

      int index=0, correct=0;
      ButtonGroup bg= new ButtonGroup();


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1_question = new javax.swing.JLabel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton3 = new javax.swing.JRadioButton();
        jRadioButton4 = new javax.swing.JRadioButton();
        jButton1_nextQ = new javax.swing.JButton();
        jButton1_previousQ = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        userNameLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1_question.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1_question.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(57, Short.MAX_VALUE)
                .addComponent(jLabel1_question, javax.swing.GroupLayout.PREFERRED_SIZE, 462, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(79, 79, 79)
                .addComponent(jLabel1_question, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(87, Short.MAX_VALUE))
        );

        jRadioButton1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jRadioButton1.setText("jRadioButton1");
        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1ActionPerformed(evt);
            }
        });

        jRadioButton2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jRadioButton2.setText("jRadioButton2");
        jRadioButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton2ActionPerformed(evt);
            }
        });

        jRadioButton3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jRadioButton3.setText("jRadioButton3");
        jRadioButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton3ActionPerformed(evt);
            }
        });

        jRadioButton4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jRadioButton4.setText("jRadioButton4");
        jRadioButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton4ActionPerformed(evt);
            }
        });

        jButton1_nextQ.setBackground(new java.awt.Color(153, 255, 102));
        jButton1_nextQ.setText("Next");
        jButton1_nextQ.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1_nextQActionPerformed(evt);
            }
        });

        jButton1_previousQ.setBackground(new java.awt.Color(255, 102, 102));
        jButton1_previousQ.setText("Previous");
        jButton1_previousQ.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1_previousQActionPerformed(evt);
            }
        });

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("time");

        jLabel2.setText("Time remaining:");

        jLabel3.setText("jLabel3");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(userNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 578, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel2)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jRadioButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 542, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jRadioButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 542, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jRadioButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 542, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jRadioButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 542, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(131, 131, 131)
                        .addComponent(jButton1_previousQ, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(99, 99, 99)
                        .addComponent(jButton1_nextQ, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(16, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jRadioButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jRadioButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1_nextQ, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1_previousQ, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addComponent(userNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1_nextQActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1_nextQActionPerformed
        // TODO add your handling code here:
        if (jButton1_nextQ.getText().equals("Restart")) {
           restartQuiz();
           return;
       }

       // If the quiz is finished, update the tries and max score in the database
       if (index == questions.length) {
           completeQuiz(correct); // Assuming 'correct' holds the current score

           jButton1_nextQ.setText("Restart");
           jButton1_previousQ.setText("Leaderboard");
           
           jLabel1_question.setText("Your score is " + correct + "/" + questions.length + ". Congratulations");
       } else {
           // Display the next question and options
           enableRbuttons(true);
           jLabel1_question.setText(questions[index]);
           jRadioButton1.setText(options[index][0]);
           jRadioButton2.setText(options[index][1]);
           jRadioButton3.setText(options[index][2]);
           jRadioButton4.setText(options[index][3]);

           if (index == questions.length - 1) {
               jButton1_nextQ.setText("Finish");
           }

           bg.clearSelection();
       }
    }//GEN-LAST:event_jButton1_nextQActionPerformed
private void restartQuiz() {
    index = 0;
    correct = 0;
    userNameLabel.setText("Welcome, " + userName + ", Tries: " + tries + "/5, Max Score: " + maxScore);
    Timer t = new Timer();
    t.start();
    jButton1_nextQ.setText("Next");
    jLabel1_question.setText(questions[index]);
    jRadioButton1.setText(options[index][0]);
    jRadioButton2.setText(options[index][1]);
    jRadioButton3.setText(options[index][2]);
    jRadioButton4.setText(options[index][3]);
    bg.clearSelection();
    enableRbuttons(true);
}

// Method to complete the quiz and update the database
private void completeQuiz(int currentScore) {
    tries++;
    String dbUrl = "jdbc:mysql://localhost/login_signup_quiz_leaderboard";
    String dbUser = "root";
    String dbPassword = ""; 

    try {
        Connection con = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        Statement st = con.createStatement();

        // Update the number of tries
        String updateTriesQuery = "UPDATE users SET tries = " + tries + " WHERE fullname = '" + userName + "'";
        st.executeUpdate(updateTriesQuery);

        // Check if the current score is higher than the max score
        if (currentScore > maxScore) {
            maxScore = currentScore;
            String updateScoreQuery = "UPDATE users SET max_score = " + maxScore + " WHERE fullname = '" + userName + "'";
            st.executeUpdate(updateScoreQuery);
        }

        if (tries >= 5) {
            JOptionPane.showMessageDialog(new JFrame(), "You have reached the maximum number of tries.", "Information", JOptionPane.INFORMATION_MESSAGE);
            jButton1_nextQ.setEnabled(false); // Disable the button after maximum tries
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(new JFrame(), "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}
    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton1ActionPerformed
        // TODO add your handling code here:
        getSelectedOption(jRadioButton1);
    }//GEN-LAST:event_jRadioButton1ActionPerformed

    private void jRadioButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton2ActionPerformed
        // TODO add your handling code here:
        getSelectedOption(jRadioButton2);
    }//GEN-LAST:event_jRadioButton2ActionPerformed

    private void jRadioButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton3ActionPerformed
        // TODO add your handling code here:
        getSelectedOption(jRadioButton3);
    }//GEN-LAST:event_jRadioButton3ActionPerformed

    private void jRadioButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton4ActionPerformed
        // TODO add your handling code here:
        getSelectedOption(jRadioButton4);
    }//GEN-LAST:event_jRadioButton4ActionPerformed

    private void jButton1_previousQActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1_previousQActionPerformed
        // TODO add your handling code here:
        if (jButton1_previousQ.getText().equals("Leaderboard")) 
        {
            // Open the leaderboard frame
            NewJFrame_leaderboard leaderboard = new NewJFrame_leaderboard();
            leaderboard.setVisible(true);
            this.setVisible(false);
            return; // Exit the method since we're showing the leaderboard
        }

        // Navigate to the previous question if index > 0
        if (index > 0) 
        {
            index--;

            // Display the previous question and options
            jLabel1_question.setText(questions[index]);
            jRadioButton1.setText(options[index][0]);
            jRadioButton2.setText(options[index][1]);
            jRadioButton3.setText(options[index][2]);
            jRadioButton4.setText(options[index][3]);

            // Restore the user's previous selection
            bg.clearSelection();
            if (userSelections[index] != -1) 
            {
                switch (userSelections[index]) 
                {
                    case 0:
                        jRadioButton1.setSelected(true);
                        break;
                    case 1:
                        jRadioButton2.setSelected(true);
                        break;
                    case 2:
                        jRadioButton3.setSelected(true);
                        break;
                    case 3:
                        jRadioButton4.setSelected(true);
                        break;
                }
            }

            enableRbuttons(true);
        }
    }//GEN-LAST:event_jButton1_previousQActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(NewJFrame_quiz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NewJFrame_quiz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NewJFrame_quiz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewJFrame_quiz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NewJFrame_quiz().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1_nextQ;
    private javax.swing.JButton jButton1_previousQ;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel1_question;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JRadioButton jRadioButton4;
    private javax.swing.JLabel userNameLabel;
    // End of variables declaration//GEN-END:variables
}
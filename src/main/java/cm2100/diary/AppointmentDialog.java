package cm2100.diary;

import javax.swing.JOptionPane;


public class AppointmentDialog extends javax.swing.JDialog {

    private Appointment appointment;
    private boolean edit;
    
    /**
     * Creates new form AppointmentDialog
     * @param parent JDialog uses
     * @param modal JDialog uses
     */
    public AppointmentDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        edit = false;
        initComponents();
    }
    
    /**
     * Overloaded AppointmentDialog that accepts appointment to be edited.
     * @param parent JDialog uses
     * @param modal JDialog uses
     * @param editAppointment appointment that is being edited
     */
    public AppointmentDialog(java.awt.Frame parent, boolean modal, Appointment editAppointment) {
        super(parent, modal);
        edit = true;
        initComponents();
        this.fillEditBoxes(editAppointment);
    }
    
    
    /**
    * Fills boxes with info from appointment that is being edited
    * @param editAppointment Appointment to be edited
    */
    private void fillEditBoxes(Appointment editAppointment) {
        // All types of appointments have description, year, month and date.
        // Filling these boxes.
        Date editDate = editAppointment.getDate();                          // Date of appointment being edited
        jTextField1.setText(editAppointment.getDescription());              // Set description
        jTextField4.setText(Integer.toString(editDate.getYear()));          // Set year
        jComboBox1.setSelectedIndex(editDate.getMonth() - 1);               // Set month
        jComboBox2.setSelectedIndex(editDate.getDay() - 1);                 // Set day
        
        // Set GUI parameters depending on what class is being edited
        if (editAppointment instanceof  UntimedRepeatAppointment) {         // Untimed Repeat
            
            // Init end date
            Date endDate = ((UntimedRepeatAppointment) editAppointment).getEndDate(); 
            
            jCheckBox1.doClick();                                           // Click "Repeat" checkbox
            
            // Set repeat type
            int repeatTypeIndex;
            switch (((UntimedRepeatAppointment) editAppointment).getRepeatType()) {
                case DAILY:
                    // Check which repeat type appointment is
                    repeatTypeIndex = 0;
                    break;
                case WEEKLY:
                    repeatTypeIndex = 1;
                    break;
                default:
                    repeatTypeIndex = 2;
                    break;
            }
            jComboBox4.setSelectedIndex(repeatTypeIndex);
            
            jTextField2.setText(Integer.toString(endDate.getYear()));           // Set end year
            jComboBox8.setSelectedIndex(endDate.getMonth() - 1);                // Set end month
            jComboBox6.setSelectedIndex(endDate.getDay() - 1);                  // Set end day
            
            
            // TEST
            // System.out.println("UNTIMED REPEAT");
            
        } else if (editAppointment instanceof  TimedRepeatAppointment) {    // Timed Repeat
            
            // ==========DEAL WITH THE FACT THAT IT IS REPEAT==========
            
            // Init end date
            Date endDate = ((TimedRepeatAppointment) editAppointment).getEndDate(); 
            
            jCheckBox1.doClick();                                           // Click "Repeat" checkbox
            
            // Set repeat type
            int repeatTypeIndex;
            switch (((TimedRepeatAppointment) editAppointment).getRepeatType()) {
                case DAILY:
                    // Check which repeat type appointment is
                    repeatTypeIndex = 0;
                    break;
                case WEEKLY:
                    repeatTypeIndex = 1;
                    break;
                default:
                    repeatTypeIndex = 2;
                    break;
            }
            jComboBox4.setSelectedIndex(repeatTypeIndex);
            
            jTextField2.setText(Integer.toString(endDate.getYear()));           // Set end year
            jComboBox8.setSelectedIndex(endDate.getMonth() - 1);                // Set end month
            jComboBox6.setSelectedIndex(endDate.getDay() - 1);                  // Set end day
            
            // ==========DEAL WITH THE FACT THAT IT IS TIMED==========
            
            // Init appointment start time and end time
            Time startTime = ((TimedRepeatAppointment) editAppointment).getStartTime();
            Time endTime = ((TimedRepeatAppointment) editAppointment).getEndTime();
            
            jCheckBox3.doClick();                                               // Click "Timed" checkbox
            // Set start and end times in checkboxes
            jComboBox9.setSelectedIndex(startTime.getHour());
            jComboBox10.setSelectedIndex(startTime.getMinute());
            jComboBox5.setSelectedIndex(endTime.getHour());
            jComboBox7.setSelectedIndex(endTime.getMinute());
            
            // TEST
            // System.out.println("TIMED REPEAT");
        } else if (editAppointment instanceof  TimedAppointment) {              // Untimed
            
            // Init appointment start time and end time
            Time startTime = ((TimedAppointment) editAppointment).getStartTime();
            Time endTime = ((TimedAppointment) editAppointment).getEndTime();
            
            jCheckBox3.doClick();                                               // Click "Timed" checkbox
            // Set start and end times in checkboxes
            jComboBox9.setSelectedIndex(startTime.getHour());
            jComboBox10.setSelectedIndex(startTime.getMinute());
            jComboBox5.setSelectedIndex(endTime.getHour());
            jComboBox7.setSelectedIndex(endTime.getMinute());
            
            // TEST
            // System.out.println("TIMED");
        } 
        
        // TEST
        // System.out.println(editAppointment.getClass());
    }
    
    
    /**
     * Returns appointment object that was created in Dialog Box.
     * @return appointment object
     */
    public Appointment getAppointment() {
        return appointment;
    }
    
    /**
     * Saves a new appointment and closes dialog.
     */
    private void save() {
        String appDescription = jTextField1.getText();
        Date appDate = new Date(Integer.parseInt(jTextField4.getText()), jComboBox1.getSelectedIndex() + 1, jComboBox2.getSelectedIndex() + 1);
        
        if (jCheckBox3.isSelected() && !jCheckBox1.isSelected()) {                                      // Timed
            Time appStartTime = new Time(jComboBox9.getSelectedIndex(), jComboBox10.getSelectedIndex());
            Time appEndTime = new Time(jComboBox5.getSelectedIndex(), jComboBox7.getSelectedIndex());
            
            TimedAppointment newAppointment = new TimedAppointment(appDescription, appDate, appStartTime, appEndTime);
            appointment = newAppointment;
            // TEST
            // System.out.println("Timed");
            // System.out.println(newAppointment.toString());
            
        } else if (jCheckBox3.isSelected() && jCheckBox1.isSelected()) {                                 // Timed Repeat
            Date appEndDate = new Date(Integer.parseInt(jTextField2.getText()), jComboBox8.getSelectedIndex() + 1, jComboBox6.getSelectedIndex() + 1);
            Time appStartTime = new Time(jComboBox9.getSelectedIndex(), jComboBox10.getSelectedIndex());
            Time appEndTime = new Time(jComboBox5.getSelectedIndex(), jComboBox7.getSelectedIndex());
            
            // Decide RepeatType
            RepeatType appRepeat;
            switch (jComboBox4.getSelectedIndex()) {
                case 0:
                    appRepeat = RepeatType.DAILY;
                    break;
                case 1:
                    appRepeat = RepeatType.WEEKLY;
                    break;
                default:
                    appRepeat = RepeatType.YEARLY;
                    break;
            }
            
            TimedRepeatAppointment newAppointment = new TimedRepeatAppointment(appDescription, appDate, appEndDate, appStartTime, appEndTime, appRepeat);
            appointment = newAppointment;
            // TEST
            // System.out.println("Timed Repeat");
            // System.out.println(newAppointment.toString());
            
        } else if (!jCheckBox3.isSelected() && !jCheckBox1.isSelected()) {                               // Untimed
            
            UntimedAppointment newAppointment = new UntimedAppointment(appDescription, appDate);
            appointment = newAppointment;
            // TEST
            // System.out.println("Untimed");
            // System.out.println(newAppointment.toString());
        } else {                                                                                         // Untimed Repeat
            Date appEndDate = new Date(Integer.parseInt(jTextField2.getText()), jComboBox8.getSelectedIndex() + 1, jComboBox6.getSelectedIndex() + 1);
            
            // Decide RepeatType
            RepeatType appRepeat;
            switch (jComboBox4.getSelectedIndex()) {
                case 0:
                    appRepeat = RepeatType.DAILY;
                    break;
                case 1:
                    appRepeat = RepeatType.WEEKLY;
                    break;
                default:
                    appRepeat = RepeatType.YEARLY;
                    break;
            }
            
            UntimedRepeatAppointment newAppointment = new UntimedRepeatAppointment(appDescription, appDate, appEndDate, appRepeat);
            appointment = newAppointment;
            
            // TEST
            // System.out.println("Untimed Repeat");
            // System.out.println(newAppointment.toString());
        }
        
        dispose();
        //this.setVisible(false);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jComboBox2 = new javax.swing.JComboBox<>();
        jCheckBox1 = new javax.swing.JCheckBox();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jComboBox4 = new javax.swing.JComboBox<>();
        jCheckBox3 = new javax.swing.JCheckBox();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jComboBox5 = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jComboBox7 = new javax.swing.JComboBox<>();
        jComboBox1 = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jComboBox9 = new javax.swing.JComboBox<>();
        jComboBox10 = new javax.swing.JComboBox<>();
        jComboBox6 = new javax.swing.JComboBox<>();
        jComboBox8 = new javax.swing.JComboBox<>();
        jTextField4 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("Description:");

        jLabel2.setText("Date:");

        jTextField2.setEnabled(false);
        jTextField2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField2KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField2KeyTyped(evt);
            }
        });

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" }));

        jCheckBox1.setText("Repeat:");
        jCheckBox1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jCheckBox1StateChanged(evt);
            }
        });

        jLabel3.setText("Repeat type:");

        jLabel4.setText("End date:");

        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Daily", "Weekly", "Yearly" }));
        jComboBox4.setEnabled(false);

        jCheckBox3.setText("Timed");
        jCheckBox3.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jCheckBox3StateChanged(evt);
            }
        });

        jLabel5.setText("Start time:");

        jLabel6.setText("End time:");

        jComboBox5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        jComboBox5.setEnabled(false);

        jLabel7.setText(":");

        jLabel8.setText(":");

        jComboBox7.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        jComboBox7.setEnabled(false);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" }));
        jComboBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox1ItemStateChanged(evt);
            }
        });

        jButton1.setText("Save");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Cancel");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jComboBox9.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        jComboBox9.setEnabled(false);

        jComboBox10.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        jComboBox10.setEnabled(false);

        jComboBox6.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" }));
        jComboBox6.setEnabled(false);

        jComboBox8.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" }));
        jComboBox8.setEnabled(false);
        jComboBox8.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox8ItemStateChanged(evt);
            }
        });

        jTextField4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField4KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField4KeyTyped(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(62, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1)
                    .addComponent(jCheckBox3)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jComboBox9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboBox10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboBox7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jCheckBox1))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addGap(2, 2, 2)
                                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jComboBox2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(jLabel3)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jButton2)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel4)
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(83, 83, 83)
                                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jComboBox8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jComboBox6, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(63, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox1)
                    .addComponent(jLabel3)
                    .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jComboBox8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jComboBox6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addComponent(jCheckBox3)
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jComboBox9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(jComboBox10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel8)
                        .addComponent(jComboBox7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addGap(20, 20, 20))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        int yearToSet;
        // Check if year entered is an integer. Show message if it is not.
        try {  
            yearToSet = Integer.parseInt(jTextField4.getText());
        }
        catch(NumberFormatException e){	
            JOptionPane.showMessageDialog(this, "Enter a valid year!");
            return;
        }

        // Do not accept appointments with year 0 since it is inexistent
        if (yearToSet < 1) {
            JOptionPane.showMessageDialog(this,"Cannot make appointments on B.C. dates.");
            return;
        }
        
        // ==========IF REPEAT APPOINTMENT==========
        // Check that start date is before end date in repeat appointments and end year is an integer
        if (jCheckBox1.isSelected()) {
            int endYearToSet;
            // Check if end year entered is an integer. Show message if it is not.
            try {  
                endYearToSet = Integer.parseInt(jTextField2.getText());
            }
            catch(NumberFormatException e) {	
                JOptionPane.showMessageDialog(this, "Enter a valid end year!");
                return;
            }
            // Check that start date is before end date
            if (endYearToSet < yearToSet) {                                                     // If end year is before start year, try again
                JOptionPane.showMessageDialog(this, "End date is before start date");
                return;
            } else if (endYearToSet == yearToSet) {                                             // If end year is same as start year, check dates
                if (jComboBox8.getSelectedIndex() < jComboBox1.getSelectedIndex()) {            // If end month is before start month, show window
                    JOptionPane.showMessageDialog(this, "End date is before start date");
                    return;
                } else if (jComboBox8.getSelectedIndex() == jComboBox1.getSelectedIndex()) {    // If end months and years are same, check days
                    if (jComboBox2.getSelectedIndex() >= jComboBox6.getSelectedIndex()) {       // If end day is same or before start day, ask to change it
                        JOptionPane.showMessageDialog(this, "Please select end date that is after start date");
                        return;
                }
            }
        }        
    }
        // ==========IF TIMED APPOINTMENT==========
        // Since appointment can start at 23:00 and end at 01:00 in some cases,
        // it is silly to make window that will say that "End time is before start time".
        // It will be annoying for some users, so end/start time are not compared.
        // Therefore, user can start appointment on 2nd January which will end on 3rd January,
        // But the calendar will only show the appointment beginning date (02/01).
        
        // If appointment was edited, ask user if he wants to save changes
        if (edit == true) {
            int choice = JOptionPane.showConfirmDialog(this,"Appointment update. Save changes?");
            switch (choice) {
                    case JOptionPane.YES_OPTION:            // Delete appointment that we were editing and create a new one on it's place
                                                    save();
                                                    break;
                    case JOptionPane.NO_OPTION:	
                                                    this.dispose();
                                                    break;
                    case JOptionPane.CANCEL_OPTION:
                    default:                        break;
            }
        } else {
            save();
        }   
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jCheckBox1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jCheckBox1StateChanged
        // TODO add your handling code here:
        // Disables/enables subsequent boxes depending on whether "Repeat" checkbox is selectected
        if(jCheckBox1.isSelected()) {               // Checkbox has been selected
            jComboBox4.setEnabled(true);
            jTextField2.setEnabled(true);
            jComboBox8.setEnabled(true);
            jComboBox6.setEnabled(true);
        } else {                                    // Checkbox has been deselected
            jComboBox4.setEnabled(false);
            jTextField2.setEnabled(false);
            jComboBox8.setEnabled(false);
            jComboBox6.setEnabled(false);
        }
    }//GEN-LAST:event_jCheckBox1StateChanged

    private void jCheckBox3StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jCheckBox3StateChanged
        // TODO add your handling code here:
        // Disables/enables subsequent boxes depending on whether "Timed" checkbox is selectected
        if(jCheckBox3.isSelected()) {               // Checkbox has been selected
            jComboBox5.setEnabled(true);
            jComboBox7.setEnabled(true);
            jComboBox9.setEnabled(true);
            jComboBox10.setEnabled(true);
        } else {                                    // Checkbox has been deselected
            jComboBox5.setEnabled(false);
            jComboBox7.setEnabled(false);
            jComboBox9.setEnabled(false);
            jComboBox10.setEnabled(false);
        }
    }//GEN-LAST:event_jCheckBox3StateChanged

    private void jTextField4KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField4KeyTyped
        // TODO add your handling code here:
        // Consumes event if typed key is not a number or year is more than 4 chars
        char typed = evt.getKeyChar();
        if(!Character.isDigit(typed) || jTextField4.getText().length() > 3) {
            evt.consume();
        }
    }//GEN-LAST:event_jTextField4KeyTyped

    private void jComboBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox1ItemStateChanged
        // TODO add your handling code here:
        // Changes number of days in ComboBox depending on the month selected
        switch (jComboBox1.getSelectedIndex()) {
            case 0:         // January
                jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" }));
                break;
            case 1:         // February
                if (jTextField4.getText().equals("") || Date.daysInMonth(Integer.parseInt(jTextField4.getText()), 2) == 28) {       // If it is not leap year, ComboBox will have 28 days
                    jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28" }));
                } else {                                                                                                            // If it is leap year, Combo will have 29 days
                    jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29" }));
                }   break;
            case 2:         // March
                jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" }));
                break;
            case 3:         // April
                jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30" }));
                break;
            case 4:         // May
                jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" }));
                break;
            case 5:         // June
                jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30" }));
                break;
            case 6:         // July
                jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" }));
                break;
            case 7:         // August
                jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" }));
                break;
            case 8:         // September
                jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30" }));
                break;
            case 9:         // October
                jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" }));
                break;
            case 10:        // November
                jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30" }));
                break;
            default:        // December
                jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" }));
                break;
        }
    }//GEN-LAST:event_jComboBox1ItemStateChanged

    private void jTextField4KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField4KeyReleased
        // TODO add your handling code here:
        // If year typed is not leap year and February is selected, ComboBox will have 28 days
        if (jComboBox1.getSelectedIndex() == 1 &&   (jTextField4.getText().equals("") || Date.daysInMonth(Integer.parseInt(jTextField4.getText()), 2) == 28)   ) {
                    jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28" }));
        } else {            // If it is leap year, Combo will have 29 days    
            jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29" }));
        }
    }//GEN-LAST:event_jTextField4KeyReleased

    private void jComboBox8ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox8ItemStateChanged
        // TODO add your handling code here:
        // Changes number of days in ComboBox depending on the month selected
        switch (jComboBox8.getSelectedIndex()) {
            case 0:         // January
                jComboBox6.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" }));
                break;
            case 1:         // February
                if (jTextField2.getText().equals("") || Date.daysInMonth(Integer.parseInt(jTextField2.getText()), 2) == 28) {       // If it is not leap year, ComboBox will have 28 days
                    jComboBox6.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28" }));
                } else {                                                                                                            // If it is leap year, Combo will have 29 days
                    jComboBox6.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29" }));
                }   break;
            case 2:         // March
                jComboBox6.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" }));
                break;
            case 3:         // April
                jComboBox6.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30" }));
                break;
            case 4:         // May
                jComboBox6.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" }));
                break;
            case 5:         // June
                jComboBox6.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30" }));
                break;
            case 6:         // July
                jComboBox6.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" }));
                break;
            case 7:         // August
                jComboBox6.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" }));
                break;
            case 8:         // September
                jComboBox6.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30" }));
                break;
            case 9:         // October
                jComboBox6.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" }));
                break;
            case 10:        // November
                jComboBox6.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30" }));
                break;
            default:        // December
                jComboBox6.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" }));
                break;
        }
    }//GEN-LAST:event_jComboBox8ItemStateChanged

    private void jTextField2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField2KeyReleased
        // TODO add your handling code here:
        // If year typed is not leap year and February is selected, ComboBox will have 28 days
        if (jComboBox8.getSelectedIndex() == 1 &&   (jTextField2.getText().equals("") || Date.daysInMonth(Integer.parseInt(jTextField2.getText()), 2) == 28)   ) {
                    jComboBox6.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28" }));
        } else {            // If it is leap year, Combo will have 29 days    
            jComboBox6.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29" }));
        }
    }//GEN-LAST:event_jTextField2KeyReleased

    private void jTextField2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField2KeyTyped
        // TODO add your handling code here:
        // Consumes event if typed key is not a number or year is more than 4 chars
        char typed = evt.getKeyChar();
        if(!Character.isDigit(typed) || jTextField2.getText().length() > 3) {
            evt.consume();
        }
    }//GEN-LAST:event_jTextField2KeyTyped

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        // Nulls the appointment if Dialog was cancelled
        appointment = null;
        // Close window if cancel was pressed
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

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
            java.util.logging.Logger.getLogger(AppointmentDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AppointmentDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AppointmentDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AppointmentDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                AppointmentDialog dialog = new AppointmentDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox10;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JComboBox<String> jComboBox5;
    private javax.swing.JComboBox<String> jComboBox6;
    private javax.swing.JComboBox<String> jComboBox7;
    private javax.swing.JComboBox<String> jComboBox8;
    private javax.swing.JComboBox<String> jComboBox9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField4;
    // End of variables declaration//GEN-END:variables
}

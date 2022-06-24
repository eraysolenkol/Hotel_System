import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import java.util.regex.*;
import java.util.Date;

public class Panel extends javax.swing.JFrame {
    RoomManager roomManager = new RoomManager();
    private final int feeInUSD = 300;
    DbHelper dbHelper = new DbHelper();
    ArrayList<Room> rooms = null;
    DefaultTableModel model;
    String dateOfToday = null;
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");  
    
    public Panel() {
        initComponents();
        model = (DefaultTableModel)table.getModel();
        rooms = roomManager.getRooms();
        populateTable();
        LocalDateTime now = LocalDateTime.now();  
        dateOfToday = dtf.format(now);
    }
    
    
    /**
    * Checks the string if empty.
    * 
    * @param  text  Any string  
    * @return       true if the text is empty. 
    */
    public boolean isTextEmpty(String text) {
        return text.equals("");
    }
    
    
    /**
    * Populates the table.
    *
    */
    public void populateTable() {
        model.setRowCount(0);
        rooms = roomManager.getRooms();
        for(Room room : rooms) {
            model.addRow(new Object[] {room.getId(),
                room.getRenter(),
                room.getDaysToStay(),
                room.getStartDate(),
                room.getMoneyToPay()
            });
        }
        table.setRowHeight(40);
    }
    
    /**
    * Cleans input texts.
    *
    */
    public void cleanTexts() {
        jTextField1.setText("");
        jTextField2.setText("");
        jTextField3.setText("");
    }
    
    
    /**
    * Validator for the date.
    *
    * @param  date         any date
    * @param  dateOfToday  the date of today
    * @return              true if the date is valid
    */
    public boolean isValidDate(String date,String dateOfToday) {
        Pattern pattern = Pattern.compile("^(0[0-9]|1[0-9]|2[0-9]|3[0-1]).(0[0-9]|1[0-2]).(2[0-9][0-9][0-9])$");
        Matcher matcher = pattern.matcher(date);
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        try {
            Date tempDate = sdf.parse(date);
            Date todaysDate = sdf.parse(dateOfToday);
            // If today's date is earlier than the input date returns false.
            if(tempDate.compareTo(todaysDate) < 0) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        
        
        return matcher.find();
    }
    
    
    /**
    * Validates the startDate and the strings and sets the label on action.
    *
    * @param  renter      name of the renter
    * @param  daysToStay  the number of days that renter will stay
    * @param  startDate   the starting day of the rent
    * @param  controller  output text label for the actions
    * @return             true if texts are not empty and startDate is valid
    */
    public boolean controlTexts(String renter, String daysToStay, String startDate, javax.swing.JLabel controller) {
            if (isTextEmpty(renter)) {
                controller.setText("Please enter a renter first!");
                return false;
            } else if (isTextEmpty(daysToStay)) {
                controller.setText("Please enter the day number first!");
                return false;
            } else if (isTextEmpty(startDate)) {
                controller.setText("Please enter the start date first!");
                return false;
            } else if (!isValidDate(startDate,dateOfToday)) {
                controller.setText("Please enter a valid date!");
                return false;
            }
            return true;
    }

    
    /**
    * Adds the renter with properties to the database.
    *
    * @param  renter      name of the renter
    * @param  daysToStay  the number of days that renter will stay
    * @param  startDate   the starting day of the rent
    * @param  id          id of the room
    * @param  controller  output text label for the actions
    * @return             true if the action is succesfull
    */
    public boolean add(String renter, String daysToStay, String startDate, int id, javax.swing.JLabel controller ) {
        Connection conn = null;
        PreparedStatement statement = null;
        try {
            conn = dbHelper.getConnection();
            statement = conn.prepareStatement("UPDATE rooms SET Renter = ?,DaysToStay = ?,StartDate = ?,MoneyToPay = ? WHERE Id = ?");
            if (!controlTexts(renter,daysToStay,startDate,controller)) {
                return false;
            }
            statement.setString(1, renter);
            statement.setInt(2, Integer.valueOf(daysToStay));
            statement.setString(3, startDate);
            statement.setInt(4,feeInUSD* Integer.valueOf(daysToStay));
            statement.setInt(5,id);
            statement.executeUpdate();
            conn.close();
            return true;
        } catch(SQLException e) {
            dbHelper.showErrorMessages(e);
            return false;
        }
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        btn = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Sitka Text", 0, 36)); // NOI18N
        jLabel1.setText("HOTEL ROOMS");

        table.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Renter", "DaysToStay", "StartDate", "MoneyToPay"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(table);
        if (table.getColumnModel().getColumnCount() > 0) {
            table.getColumnModel().getColumn(0).setResizable(false);
            table.getColumnModel().getColumn(1).setResizable(false);
            table.getColumnModel().getColumn(2).setResizable(false);
            table.getColumnModel().getColumn(3).setResizable(false);
            table.getColumnModel().getColumn(4).setResizable(false);
        }

        btn.setFont(new java.awt.Font("Segoe UI", 0, 28)); // NOI18N
        btn.setText("Rent Room");
        btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel2.setText("Renter:");

        jTextField1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel3.setText("Days To Stay:");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel4.setText("Starting Date:");

        jTextField2.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N

        jTextField3.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(217, 217, 217)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(27, 27, 27)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(143, 143, 143)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 796, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(289, 289, 289)
                        .addComponent(btn)))
                .addContainerGap(226, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(474, 474, 474))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)))
                        .addGap(33, 33, 33)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(2, 2, 2)))
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addComponent(btn)
                .addGap(40, 40, 40))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActionPerformed
        int row = table.getSelectedRow();
        if (row == -1) {
            jLabel5.setText("Please select a room first!");
            return;
        }
        int id = Integer.valueOf(table.getModel().getValueAt(row, 0).toString());
        if (add(jTextField1.getText(), jTextField2.getText(), jTextField3.getText(), id, jLabel5)) {
            jLabel5.setText("Welcome " + jTextField1.getText() + "!");
            cleanTexts();
            populateTable();
        }  
    }//GEN-LAST:event_btnActionPerformed

    public static void main(String args[]) {

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Panel().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTable table;
    // End of variables declaration//GEN-END:variables
}

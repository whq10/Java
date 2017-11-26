import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class TestDemo {

   private static String myfileName;
   private static String data_details;
   private Frame mainFrame;
   private Label headerLabel;
   private Label statusLabel;
   private Panel controlPanel;
   final static List myList = new List(4,false);
   private boolean isAdd = true;

   
   Label  idlabel= new Label("ID: ", Label.RIGHT);
   Label  nameLabel = new Label("Name: ", Label.CENTER);    
   Button saveButton = new Button("Save");
   final static TextField idText = new TextField(6);
   final static TextField nameText = new TextField(6);

   public TestDemo(){
      prepareGUI();
   }

   public static void main(String[] args){
	   myfileName = "d:\\student.txt";
	   TestDemo  awtControlDemo = new TestDemo();
      awtControlDemo.Display();
   }

   private void prepareGUI(){
      mainFrame = new Frame("Test Demo");
      mainFrame.setSize(1000,800);
      mainFrame.setLayout(new GridLayout(3, 1));
      mainFrame.addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent windowEvent){
            System.exit(0);
         }        
      });    
      headerLabel = new Label();
      headerLabel.setAlignment(Label.CENTER);
      statusLabel = new Label();        
      statusLabel.setAlignment(Label.CENTER);
      statusLabel.setSize(350,50);

      controlPanel = new Panel();
      controlPanel.setLayout(new FlowLayout());

      mainFrame.add(headerLabel);
      mainFrame.add(controlPanel);
      mainFrame.add(statusLabel);
      mainFrame.setVisible(true);  
   }

   private static void ReadFromFile(List myList)
   {
	   myList.clear();
       String fileName_1 = myfileName;

       try {
           byte[] buffer = new byte[256];

           FileInputStream inputStream = 
               new FileInputStream(fileName_1);
           int total = 0;
           int nRead = 0;
           while((nRead = inputStream.read(buffer)) != -1) {
        	   String tempStr = new String(buffer);
        	   String[] details = tempStr.split(":");
        	   String idStr = details[0];
        	   myList.add(idStr);    	   
               System.out.println(tempStr);
               total += nRead;
           }   

           inputStream.close();        
           System.out.println("Read " + total + " bytes");
       }
       catch(FileNotFoundException ex) {
           System.out.println(
               "Unable to open file '" + 
               fileName_1 + "'");                
       }
       catch(IOException ex) {
           System.out.println(
               "Error reading file '" 
               + fileName_1 + "'");                  
       }
   
   }
   
   private static void WriteToFile(String details)
   {
       String fileName_1 = myfileName;
       try {
           byte[] temp = details.getBytes();
           byte[] buffer = new byte[256];
           for(int i=0; i < buffer.length; i++)
           {
        	   if(i<temp.length)
        	   {
            	   buffer[i] = temp[i];        		   
        	   }	   
           }

           FileOutputStream outputStream =
               new FileOutputStream(fileName_1,true);
           outputStream.write(buffer);
           outputStream.close();       
           System.out.println("Wrote " + buffer.length + 
               " bytes");
       }
       catch(IOException ex) {
           System.out.println(
               "Error writing file '"
               + fileName_1 + "'");
       }
   
   }
   
   private static void SaveForEdit()
   {
 	  FileInputStream inputStream = null;
	  FileOutputStream outputStream = null;
       String fileName_2 = myfileName+".bak";
       try {
           outputStream =
               new FileOutputStream(fileName_2,true);
       }
       catch(IOException ex) {
           System.out.println(
               "Error writing file '"
               + fileName_2 + "'");
       }
        
       String current_id = myList.getSelectedItem();
       String fileName_1 = myfileName;
           byte[] buffer = new byte[256];
			try {
				inputStream = new FileInputStream(fileName_1);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
           int total = 0;
           int nRead = 0;
           String[] details = null;
           String tempStr;
           try {
        	   String tempStr_1 = null;
				while((nRead = inputStream.read(buffer)) != -1) {
				   tempStr = new String(buffer);
				   details = tempStr.split(":");
				   String idStr = details[0];

				   if(idStr.equals(current_id))
				   {
					 tempStr_1 = idText.getText() + ":" + nameText.getText();
			         byte[] temp = tempStr_1.getBytes();
	                  byte[] tempBuffer = new byte[256];
	                  for(int i=0; i < buffer.length; i++)
	                  {
	               	   if(i<temp.length)
	               	   {
	               		tempBuffer[i] = temp[i];        		   
	               	   }	   
	                  }
					   outputStream.write(tempBuffer);
				   }
				   else
				   {
	                  outputStream.write(buffer); 						   
				   }
				      System.out.println(tempStr_1);
				      total += nRead;
				  }
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}   
			//idText.setText(current_id);
           //nameText.setText(details[1]);
           try {
				inputStream.close();
				outputStream.close();   
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}        
         System.out.println("Wrote " + buffer.length + 
             " bytes");
         
         //rename and backup file
         File newfile = new File(fileName_2);
         File tempFile = new File(fileName_1+".temp");
         if(newfile.renameTo(tempFile)){
             System.out.println("File rename success");;
         }else{
             System.out.println("File rename failed");
         }
         
         File oldfile = new File(fileName_1);
         File bakFile = new File(fileName_1+".old");
         if(oldfile.renameTo(bakFile)){
             System.out.println("File rename success");;
         }else{
             System.out.println("File rename failed");
         }
         
         File tempfile_1 = new File(fileName_1+".temp");
         File finalFile = new File(fileName_1);
         if(tempfile_1.renameTo(finalFile)){
             System.out.println("File rename success");;
         }else{
             System.out.println("File rename failed");
         }

         File rem_file = new File(fileName_1+".old");
         if(rem_file.delete()){
             System.out.println("File rename success");;
         }else{
             System.out.println("File rename failed");
         }     
         nameText.setEnabled(false);
	  
   }
   
   private void Display(){   
      headerLabel.setText("Test Demo for STUDENT & COURSE & GRADE management"); 
      CheckboxGroup fruitGroup = new CheckboxGroup();
      Checkbox chkStudent = new Checkbox("Student",fruitGroup,true);
      Checkbox chkCourse = new Checkbox("Course",fruitGroup,false);
      Checkbox chkGrade = new Checkbox("Grade",fruitGroup,false);

      statusLabel.setText("Apple Checkbox: checked");
      chkStudent.addItemListener(new ItemListener() {
         public void itemStateChanged(ItemEvent e) {             
        	 myfileName = "d:\\student.txt";
             statusLabel.setText("Student selected:" + myfileName);
             idlabel.setText("Student ID:");
             nameLabel.setText("Student Name");
             ReadFromFile(myList);
                         
         }
      });

      chkCourse.addItemListener(new ItemListener() {
         public void itemStateChanged(ItemEvent e) {
        	 myfileName = "d:\\course.txt";
             statusLabel.setText("Course selected:" + myfileName);
             idlabel.setText("Course ID:");
             nameLabel.setText("Course Name");
             ReadFromFile(myList);
         }
      });

      chkGrade.addItemListener(new ItemListener() {
         public void itemStateChanged(ItemEvent e) {
        	 myfileName = "d:\\grade.txt";
            statusLabel.setText("Grade selected:" + myfileName);
            idlabel.setText("Grade ID:");
            nameLabel.setText("Grade Name");
            ReadFromFile(myList);
         }
      });

      ReadFromFile(myList);
      
      Button showButton = new Button("Show");
      Button addButton = new Button("Add");
      Button editButton = new Button("Edit");
      
      showButton.addActionListener(new ActionListener() {

         public void actionPerformed(ActionEvent e) { 
        	 FileInputStream inputStream = null;
             idlabel.setVisible(true);
             idText.setVisible(true);
             idText.setEnabled(false);
             nameLabel.setVisible(true);
             nameText.setVisible(true);
             nameText.setEnabled(false);
             saveButton.setVisible(true);
             mainFrame.setVisible(true);  
        	       	 
            String data = "MyList Selected: " 
               + myList.getItem(myList.getSelectedIndex());
            statusLabel.setText(data);
            
            
            String current_id = myList.getSelectedItem();
            String fileName_1 = myfileName;
                byte[] buffer = new byte[256];
				try {
					inputStream = new FileInputStream(fileName_1);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
                int total = 0;
                int nRead = 0;
                String[] details = null;
                String tempStr;
                try {
					while((nRead = inputStream.read(buffer)) != -1) {
					   tempStr = new String(buffer);
					   details = tempStr.split(":");
					   String idStr = details[0];

					   if(idStr.equals(current_id))
						   break;             		   
					      System.out.println(tempStr);
					      total += nRead;
					  }
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}   
				idText.setText(current_id);
                nameText.setText(details[1]);
                try {
					inputStream.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}    
            
         }
      }); 
      
      addButton.addActionListener(new ActionListener() {

          public void actionPerformed(ActionEvent e) {  
              idlabel.setVisible(true);
              idText.setVisible(true);
              idText.setEnabled(true);
              idText.setText("");            
              nameLabel.setVisible(true);
              nameText.setVisible(true);
              nameText.setEnabled(true);
              nameText.setText("");              
              saveButton.setVisible(true);
              mainFrame.setVisible(true);  
          }
       }); 
      
      editButton.addActionListener(new ActionListener() {

          public void actionPerformed(ActionEvent e) {  
        	  isAdd = false;
        	  FileInputStream inputStream = null;
              idlabel.setVisible(true);
              idText.setVisible(true);
              idText.setEnabled(false);
              nameLabel.setVisible(true);
              nameText.setVisible(true);
              nameText.setEnabled(true);
              saveButton.setVisible(true);
              mainFrame.setVisible(true);             
              mainFrame.setVisible(true);            
              String current_id = myList.getSelectedItem();
              String fileName_1 = myfileName;
                  byte[] buffer = new byte[256];
  				try {
  					inputStream = new FileInputStream(fileName_1);
  				} catch (FileNotFoundException e1) {
  					// TODO Auto-generated catch block
  					e1.printStackTrace();
  				}
                  int total = 0;
                  int nRead = 0;
                  String[] details = null;
                  String tempStr;
                  try {
  					while((nRead = inputStream.read(buffer)) != -1) {
  					   tempStr = new String(buffer);
  					   details = tempStr.split(":");
  					   String idStr = details[0];

  					   if(idStr.equals(current_id))
  						   break;             		   
  					      System.out.println(tempStr);
  					      total += nRead;
  					  }
  				} catch (IOException e1) {
  					// TODO Auto-generated catch block
  					e1.printStackTrace();
  				}   
  				idText.setText(current_id);
                  nameText.setText(details[1]);
                  try {
  					inputStream.close();
  				} catch (IOException e1) {
  					// TODO Auto-generated catch block
  					e1.printStackTrace();
  				}    
          }
       }); 

      controlPanel.add(chkStudent);
      controlPanel.add(chkCourse);   
      controlPanel.add(chkGrade);    
      controlPanel.add(myList);
      controlPanel.add(showButton);
      controlPanel.add(addButton);
      controlPanel.add(editButton);
      idlabel.setVisible(false);
      idText.setVisible(false);
      nameLabel.setVisible(false);
      nameText.setVisible(false);
      saveButton.setVisible(false);
  
      saveButton.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) { 
        	  if(isAdd)
        	  {
        		  boolean isThere = false;
            	  data_details = idText.getText() + ":" + nameText.getText();
            	  String[] tempS = myList.getItems();
            	  for(int i=0;i<tempS.length;i++)
            	  {
            		  if(tempS[i].equals(idText.getText()))
            		  {
            			  isThere = true;
            			  
            		  }         		  
            	  }
            	  if(!isThere)
            	  {
            		  myList.add(idText.getText());
            	  }     	  
            	  idText.setEnabled(false);
            	  nameText.setEnabled(false);
            	  WriteToFile(data_details);   		  
        	  }
        	  else
        	  {
        		  SaveForEdit();
        	  }
        	  
        	  
          }
       }); 
      
      controlPanel.add(idlabel);
      controlPanel.add(idText);
      controlPanel.add(nameLabel);       
      controlPanel.add(nameText);
      controlPanel.add(saveButton);
      mainFrame.setVisible(true);  
   }
   
}
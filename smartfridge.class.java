// Source code is decompiled from a .class file using FernFlower decompiler (from Intellij IDEA).
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class SmartFridge {
   static final String URL = "jdbc:mysql://localhost:3306/smartfridge";
   static final String USER = "root";
   static final String PASSWORD = "jeonjungkook";
   static Connection con;
   static Scanner sc;

   public SmartFridge() {
   }

   public static void main(String[] var0) {
      try {
         Class.forName("com.mysql.cj.jdbc.Driver");
         con = DriverManager.getConnection("jdbc:mysql://localhost:3306/smartfridge", "root", "jeonjungkook");
         int var1 = -1;

         do {
            System.out.println("\n===== SMART REFRIGERATOR =====");
            System.out.println("1. Add Product");
            System.out.println("2. View Products");
            System.out.println("3. Update Product");
            System.out.println("4. Delete Product");
            System.out.println("5. Near Expiry Products");
            System.out.println("6. Expired Products");
            System.out.println("7. Exit");
            System.out.print("Enter Choice: ");
            if (!sc.hasNextInt()) {
               System.out.println("Invalid input. Please enter a number.");
               sc.nextLine();
            } else {
               var1 = sc.nextInt();
               sc.nextLine();

               try {
                  switch (var1) {
                     case 1:
                        addProduct();
                        break;
                     case 2:
                        viewProducts();
                        break;
                     case 3:
                        updateProduct();
                        break;
                     case 4:
                        deleteProduct();
                        break;
                     case 5:
                        nearExpiry();
                        break;
                     case 6:
                        expiredProducts();
                        break;
                     case 7:
                        System.out.println("Thank You!");
                        break;
                     default:
                        System.out.println("Invalid Choice");
                  }
               } catch (SQLException var14) {
                  System.out.println("Database error: " + var14.getMessage());
               }
            }
         } while(var1 != 7);
      } catch (ClassNotFoundException var15) {
         System.out.println("MySQL JDBC driver not found. Make sure the connector JAR is on the classpath.");
      } catch (SQLException var16) {
         System.out.println("Could not connect to the database: " + var16.getMessage());
      } finally {
         if (con != null) {
            try {
               con.close();
            } catch (SQLException var13) {
               System.out.println("Error closing connection: " + var13.getMessage());
            }
         }

         sc.close();
      }

   }

   static void addProduct() throws SQLException {
      System.out.print("Product Name: ");
      String var0 = sc.nextLine();
      System.out.print("Category: ");
      String var1 = sc.nextLine();

      while(true) {
         System.out.print("Quantity: ");
         if (sc.hasNextInt()) {
            int var2 = sc.nextInt();
            sc.nextLine();
            System.out.print("Date Added (YYYY-MM-DD): ");
            String var3 = sc.nextLine();
            System.out.print("Expiry Date (YYYY-MM-DD): ");
            String var4 = sc.nextLine();
            String var5 = "INSERT INTO products(name,category,quantity,date_added,expiry_date) VALUES(?,?,?,?,?)";
            PreparedStatement var6 = con.prepareStatement(var5);

            try {
               var6.setString(1, var0);
               var6.setString(2, var1);
               var6.setInt(3, var2);
               var6.setString(4, var3);
               var6.setString(5, var4);
               var6.executeUpdate();
            } catch (Throwable var10) {
               if (var6 != null) {
                  try {
                     var6.close();
                  } catch (Throwable var9) {
                     var10.addSuppressed(var9);
                  }
               }

               throw var10;
            }

            if (var6 != null) {
               var6.close();
            }

            System.out.println("Product Added Successfully!");
            return;
         }

         System.out.println("Please enter a valid number.");
         sc.nextLine();
      }
   }

   static void viewProducts() throws SQLException {
      String var0 = "SELECT * FROM products";
      Statement var1 = con.createStatement();

      try {
         ResultSet var2 = var1.executeQuery(var0);

         try {
            System.out.printf("%n%-4s%-15s%-15s%-6s%-12s%-12s%n", "ID", "Name", "Category", "Qty", "Added", "Expiry");
            boolean var3 = false;

            while(var2.next()) {
               var3 = true;
               System.out.printf("%-4d%-15s%-15s%-6d%-12s%-12s%n", var2.getInt("id"), var2.getString("name"), var2.getString("category"), var2.getInt("quantity"), var2.getDate("date_added"), var2.getDate("expiry_date"));
            }

            if (!var3) {
               System.out.println("No products found.");
            }
         } catch (Throwable var7) {
            if (var2 != null) {
               try {
                  var2.close();
               } catch (Throwable var6) {
                  var7.addSuppressed(var6);
               }
            }

            throw var7;
         }

         if (var2 != null) {
            var2.close();
         }
      } catch (Throwable var8) {
         if (var1 != null) {
            try {
               var1.close();
            } catch (Throwable var5) {
               var8.addSuppressed(var5);
            }
         }

         throw var8;
      }

      if (var1 != null) {
         var1.close();
      }

   }

   static void updateProduct() throws SQLException {
      System.out.print("Enter Product ID: ");
      if (!sc.hasNextInt()) {
         System.out.println("Invalid ID.");
         sc.nextLine();
      } else {
         int var0 = sc.nextInt();
         System.out.print("New Quantity: ");
         if (!sc.hasNextInt()) {
            System.out.println("Invalid quantity.");
            sc.nextLine();
         } else {
            int var1 = sc.nextInt();
            sc.nextLine();
            String var2 = "UPDATE products SET quantity=? WHERE id=?";
            PreparedStatement var3 = con.prepareStatement(var2);

            try {
               var3.setInt(1, var1);
               var3.setInt(2, var0);
               int var4 = var3.executeUpdate();
               if (var4 == 0) {
                  System.out.println("No product found with that ID.");
               } else {
                  System.out.println("Updated Successfully!");
               }
            } catch (Throwable var7) {
               if (var3 != null) {
                  try {
                     var3.close();
                  } catch (Throwable var6) {
                     var7.addSuppressed(var6);
                  }
               }

               throw var7;
            }

            if (var3 != null) {
               var3.close();
            }

         }
      }
   }

   static void deleteProduct() throws SQLException {
      System.out.print("Enter Product ID: ");
      if (!sc.hasNextInt()) {
         System.out.println("Invalid ID.");
         sc.nextLine();
      } else {
         int var0 = sc.nextInt();
         sc.nextLine();
         String var1 = "DELETE FROM products WHERE id=?";
         PreparedStatement var2 = con.prepareStatement(var1);

         try {
            var2.setInt(1, var0);
            int var3 = var2.executeUpdate();
            if (var3 == 0) {
               System.out.println("No product found with that ID.");
            } else {
               System.out.println("Deleted Successfully!");
            }
         } catch (Throwable var6) {
            if (var2 != null) {
               try {
                  var2.close();
               } catch (Throwable var5) {
                  var6.addSuppressed(var5);
               }
            }

            throw var6;
         }

         if (var2 != null) {
            var2.close();
         }

      }
   }

   static void nearExpiry() throws SQLException {
      String var0 = "SELECT * FROM products WHERE expiry_date >= CURDATE() AND expiry_date <= DATE_ADD(CURDATE(), INTERVAL 3 DAY)";
      Statement var1 = con.createStatement();

      try {
         ResultSet var2 = var1.executeQuery(var0);

         try {
            System.out.println("\nNear Expiry Products:");
            boolean var3 = false;

            while(var2.next()) {
               var3 = true;
               PrintStream var10000 = System.out;
               String var10001 = var2.getString("name");
               var10000.println(var10001 + " -> " + String.valueOf(var2.getDate("expiry_date")));
            }

            if (!var3) {
               System.out.println("None.");
            }
         } catch (Throwable var7) {
            if (var2 != null) {
               try {
                  var2.close();
               } catch (Throwable var6) {
                  var7.addSuppressed(var6);
               }
            }

            throw var7;
         }

         if (var2 != null) {
            var2.close();
         }
      } catch (Throwable var8) {
         if (var1 != null) {
            try {
               var1.close();
            } catch (Throwable var5) {
               var8.addSuppressed(var5);
            }
         }

         throw var8;
      }

      if (var1 != null) {
         var1.close();
      }

   }

   static void expiredProducts() throws SQLException {
      String var0 = "SELECT * FROM products WHERE expiry_date < CURDATE()";
      Statement var1 = con.createStatement();

      try {
         ResultSet var2 = var1.executeQuery(var0);

         try {
            System.out.println("\nExpired Products:");
            boolean var3 = false;

            while(var2.next()) {
               var3 = true;
               PrintStream var10000 = System.out;
               String var10001 = var2.getString("name");
               var10000.println(var10001 + " -> " + String.valueOf(var2.getDate("expiry_date")));
            }

            if (!var3) {
               System.out.println("None.");
            }
         } catch (Throwable var7) {
            if (var2 != null) {
               try {
                  var2.close();
               } catch (Throwable var6) {
                  var7.addSuppressed(var6);
               }
            }

            throw var7;
         }

         if (var2 != null) {
            var2.close();
         }
      } catch (Throwable var8) {
         if (var1 != null) {
            try {
               var1.close();
            } catch (Throwable var5) {
               var8.addSuppressed(var5);
            }
         }

         throw var8;
      }

      if (var1 != null) {
         var1.close();
      }

   }

   static {
      sc = new Scanner(System.in);
   }
}

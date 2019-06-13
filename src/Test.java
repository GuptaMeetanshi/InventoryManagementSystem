import java.util.*;
import java.sql.*;
class Customer
{
    static Connection con;
    static Scanner sc=new Scanner(System.in);
    static
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");//to load the driver
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/IMS","root","Meetan21#shi");
            if(con!=null)
            {
                System.out.println("Connection Open.....");
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }

    public void addNewProduct() throws SQLException
    {
        System.out.println("Enter the product id : ");
        int pId=sc.nextInt();
        System.out.println("Enter the product name :");
        sc.nextLine();
        String pName=sc.nextLine();
        System.out.println("Enter the purchase price of the product :");
        int purchasePrice=sc.nextInt();
        System.out.println("Enter the sale price of product :");
        float salePrice=sc.nextFloat();
        System.out.println("Enter the quantity of product :");
        int productQty=sc.nextInt();
        PreparedStatement pst=con.prepareStatement("insert into Product values(?,?,?,?,?)");
        pst.setInt(1,pId);
        pst.setString(2,pName);
        pst.setInt(3,purchasePrice);
        pst.setFloat(4,salePrice);
        pst.setInt(5,productQty);
        if(pst.executeUpdate()>0)
        {
            System.out.println("DATA INSERTED SUCCESSFULLY");
        }
        else
        {

            System.out.println("DATA INSERTED SUCCESSFULLY");
        }


    }
    public  void viewAllProduct() throws SQLException
    {
        System.out.println("                                                                           ");
        System.out.println("                                                                           ");
        System.out.println("                                                                           ");
        PreparedStatement pst=con.prepareStatement("select * from Product");
        ResultSet rs=pst.executeQuery();
        while(rs.next())
        {

            System.out.println(rs.getInt(1)+"\t\t"+rs.getString(2)+"\t\t"+rs.getInt(3)+"\t\t"+rs.getFloat(4)+"\t\t"+rs.getInt(5));

        }
    }
    public  void removeProduct() throws SQLException
    {
        System.out.println("Enter the Product Id of product to be deleted");
        int id=sc.nextInt();
        PreparedStatement pst=con.prepareStatement("select * from Product where productId=?");//parametarized Query
        pst.setInt(1,id);
        ResultSet rs=pst.executeQuery();
        if(rs.next())
        {
            pst=con.prepareStatement("delete  from Product where productId=?");
            pst.setInt(1,id);
            if(pst.executeUpdate()>0)
            {
                System.out.println("RECORD DELETED");
            }
            else
            {
                System.out.println("RECORD DELETED");
            }
        }
        else
        {
            System.out.println("The record is not found.......");
        }
    }
    public void addProductSaleDetail()  throws Exception
    {
        System.out.println("Enter the sale id : ");
        int saleId=sc.nextInt();
        System.out.println("Enter the product id :");
        int pId=sc.nextInt();
        System.out.println("Enter the  price of the product :");
        Float price=sc.nextFloat();
        System.out.println("Enter the date in MM/DD/YYYY:");
        sc.nextLine();
        String date=sc.nextLine();
        System.out.println("Enter the quantity of product which is sold");
        int saleQty=sc.nextInt();
        PreparedStatement pst=con.prepareStatement("insert into Sale values(?,?,?,?,?)");
        pst.setInt(1,saleId);
        pst.setInt(2,pId);
        pst.setFloat(3,price);
        java.util.Date d=new java.util.Date(date);
        java.sql.Date d1=new java.sql.Date(d.getYear(),d.getMonth(),d.getDate());
        pst.setDate(4,d1);
        pst.setInt(5,saleQty);
        if(pst.executeUpdate()>0)
        {
            pst=con.prepareStatement("select productQty from Product where productId=?");
            pst.setInt(1,pId);
            ResultSet rs=pst.executeQuery();
            int temp=0;
            if(rs.next())
            {
                temp=rs.getInt(1);
            }
            int finalQty=temp-saleQty;
            pst=con.prepareStatement("update product set productQty=? where productId=?");
            pst.setInt(1,finalQty);
            pst.setInt(2,pId);
            pst.executeUpdate();
            System.out.println("DATA SUCESSFULLY INSERTED IN SALE TABLE");
        }
        else
        {
            System.out.println("DATA NOT INSERTED IN SALE TABLE");
        }

    }
    public void viewProductSale() throws Exception
    {

        System.out.println("                                                                           ");
        System.out.println("                                                                           ");
        System.out.println("                                                                           ");
        PreparedStatement pst=con.prepareStatement("select * from Sale");
        ResultSet rs=pst.executeQuery();
        while(rs.next())
        {


            System.out.println(rs.getInt(1)+"\t"+rs.getInt(2)+"\t"+rs.getFloat(3)+"\t"+rs.getDate(4).toString()+"\t"+rs.getInt(5));

        }
    }
    public void updateProductSale() throws Exception
    {
        System.out.println("Enter the sale id you want to update");
        int sId=sc.nextInt();
        PreparedStatement pst=con.prepareStatement("select * from Sale where saleId=?");
        pst.setInt(1,sId);
        ResultSet rs=pst.executeQuery();
        if(rs.next())
        {
            pst=con.prepareStatement("update Sale set saleQty=? where saleId=?");
            System.out.println("Enter the new sale");
            int qty=sc.nextInt();
            pst.setInt(1,qty);
            pst.setInt(2,sId);

            if(pst.executeUpdate()>0)
            {
                System.out.println("RECORD UPDATED");
            }
            else
            {
                System.out.println("RECORD NOT UPDATED");
            }
        }
        else
        {
            System.out.println("The record not found");
        }
    }
    public void viewProductStock() throws SQLException
    {
        System.out.println("                                                                           ");
        System.out.println("                                                                           ");
        System.out.println("                                                                           ");

        PreparedStatement pst=con.prepareStatement("select productId,productName,productQty from Product");
        ResultSet rs=pst.executeQuery();

        while(rs.next())
        {
            System.out.println(rs.getInt(1)+"\t"+rs.getString(2)+"\t"+rs.getInt(3));

        }



    }
    public void viewProfitDetails() throws Exception
    {

        System.out.println("Enter date between which profit is to be calculatd");
        System.out.print("\t\t\t\t");
        String date1=sc.next();
        System.out.print("\t\t\t\t");
        String date2=sc.next();
        double tp=0;
        java.util.Date d=new java.util.Date(date1);
        java.sql.Date d1=new java.sql.Date(d.getYear(),d.getMonth(),d.getDate());
        java.util.Date d2=new java.util.Date(date2);
        java.sql.Date d3=new java.sql.Date(d2.getYear(),d2.getMonth(),d2.getDate());
        int pid=0;
        int QT=0;
        clearscreen();
        PreparedStatement pst=con.prepareStatement("select * from Sale where date > ?&&date <?");		//passing the SQL select Query to the Sale table
        pst.setDate(1,d1);
        pst.setDate(2,d3);
        ResultSet rs=pst.executeQuery();		//storing the result set from the Sale table
        System.out.println("\n\n\n");
        System.out.println("\t\t\t\tDETAILS OF PROFIT\n");
        System.out.println("\t\tProductId\tProductName\tPurchasePrice\tSalePrice\tProfit\t\tDate");
        while(rs.next())
        {
            pid=rs.getInt(2);
            QT=rs.getInt(5);
            PreparedStatement pst1=con.prepareStatement("select * from Product where productId=?");		//Passing the SQL select Query to the product table
            pst1.setInt(1,pid);
            ResultSet rs1=pst1.executeQuery();		//Storing the Results obtained from the product table
            while(rs1.next())
            {
                int pp=rs1.getInt(3);
                double sp=rs1.getDouble(4);
                System.out.println("\t\t"+rs1.getInt(1)+"\t\t"+rs1.getString(2)+"\t \t"+pp+"\t\t"+sp+"\t\t"+((sp-pp)*QT)+"\t\t"+rs.getDate(4));
                tp=tp+((sp-pp)*QT);
            }
        }
        System.out.println("\n\t\t\t\t\tTotal Profit = "+tp);

    }


    public static void clearscreen()
    {
        try
        {
            new ProcessBuilder("cmd","/c","cls").inheritIO().start().waitFor();
        }
        catch(Exception e)
        {
        }
    }
    public int login() throws Exception
    {
        clearscreen();
        System.out.println("                                                                           ");
        System.out.println("                                                                           ");
        System.out.println("                                                                           ");
        System.out.println("                                                                           ");
        System.out.println("                                                                           ");
        System.out.println("                                                                           ");
        System.out.println("                                                                           ");
        System.out.println("                                                                           ");
        System.out.println("                                                                           ");
        System.out.println("\t\t--------------------------------------------\t\t\t");
        System.out.println("\t\t|             Enter the username           |\t\t\t");
        System.out.println("\t\t--------------------------------------------\t\t\t");
        System.out.print("\t\t\t\t");
        String username=sc.nextLine();
        System.out.println("                                                                           ");
        System.out.println("                                                                           ");
        System.out.println("                                                                           ");

        System.out.println("\t\t--------------------------------------------\t\t\t");
        System.out.println("\t\t|             Enter the password           |\t\t\t");
        System.out.println("\t\t--------------------------------------------\t\t\t");
        System.out.print("\t\t\t\t");
        String pass=sc.nextLine();
        PreparedStatement pst=con.prepareStatement("select * from Login where userName=?");
        pst.setString(1,username);
        ResultSet rs=pst.executeQuery();
        String username1=null;
        String password1=null;
        while(rs.next())
        {
            username1=rs.getString(1);
            password1=rs.getString(2);

        }
        if(username.equals(username1)&&pass.equals(password1))
        {
            return 1;
        }
        else
        {
            return 0;
        }
    }

    public void menu()
    {
        clearscreen();


        System.out.println("                                                                           ");
        System.out.println("                                                                           ");
        System.out.println("                                                                           ");
        System.out.println("                                                                           ");
        System.out.println("                                                                           ");
        System.out.println("                                                                           ");
        System.out.println("                                                                           ");
        System.out.println("                                                                           ");
        System.out.println("                                                                           ");
        System.out.println("                                    -----------                            ");
        System.out.println("                                     MAIN MENU                             ");
        System.out.println("                                    -----------                            ");
        System.out.println("                                                                           ");
        System.out.println("                                                                           ");
        System.out.println("                                                                           ");
        System.out.println("      *********************************************************************");
        System.out.println("      *                 Enter your choice on following basis              *");
        System.out.println("      *********************************************************************");
        System.out.println("      *                                                                   *");
        System.out.println("      *                                                                   *");
        System.out.println("      *                                                                   *");
        System.out.println("      *                                                                   *");
        System.out.println("      *                                                                   *");
        System.out.println("      *                                                                   *");
        System.out.println("      *                         1.Manage Product                          *");
        System.out.println("      *                         2.Manage Sale                             *");
        System.out.println("      *                         3.Manage Stock                            *");
        System.out.println("      *                         4.View Profit Details                     *");
        System.out.println("      *                         5.Exit                                    *");
        System.out.println("      *                                                                   *");
        System.out.println("      *                                                                   *");
        System.out.println("      *                                                                   *");
        System.out.println("      *                                                                   *");
        System.out.println("      *                                                                   *");
        System.out.println("      *                                                                   *");
        System.out.println("      *********************************************************************");
        System.out.println("                                                                           ");
        System.out.println("                                                                           ");
        System.out.println("                                                                           ");
        System.out.println("                                                                           ");
        System.out.println("                                                                           ");
        System.out.println("                                                                           ");
        System.out.println("                                                                           ");
        System.out.println("                                                                           ");
        System.out.println("                                                                           ");
        System.out.println("                                                                           ");
        System.out.println("                                                                           ");
        System.out.println("                                                                           ");
        System.out.println("                                                                           ");
    }

}
class Test
{
    public static void main(String[] args)
    {
        Scanner sc=new Scanner(System.in);



        Customer ob=new Customer();
        int temp=0;
        try
        {
            temp=ob.login();
        }
        catch(Exception E)
        {
        }
        if(temp==1)
        {
            while(true){
                ob.clearscreen();
                ob.menu();
                System.out.println("Enter your choice :");
                int ch=sc.nextInt();


                System.out.println("                                                                           ");
                System.out.println("                                                                           ");
                System.out.println("                                                                           ");
                System.out.println("                                                                           ");
                System.out.println("                                                                           ");
                System.out.println("                                                                           ");
                System.out.println("                                                                           ");
                System.out.println("                                                                           ");
                System.out.println("                                                                           ");
                switch(ch)
                {
                    case 1:
                        ob.clearscreen();
                        System.out.println("                                                                           ");
                        System.out.println("                                                                           ");
                        System.out.println("                                                                           ");
                        System.out.println("                                                                           ");
                        System.out.println("                                                                           ");
                        System.out.println("                                                                           ");
                        System.out.println("                                                                           ");
                        System.out.println("                                                                           ");
                        System.out.println("                                                                           ");
                        System.out.println("\t\t---------------------------------------------------");
                        System.out.println("\t\t\tEnter\n\t\t\t1 to add the new Product\n      \t\t\t2 to view all Product\n      \t\t\t3 to remove a Product " );
                        System.out.println("\t\t---------------------------------------------------");
                        System.out.println("Enter your choice :");
                        int ch1=sc.nextInt();
                        ob.clearscreen();
                        switch(ch1)
                        {
                            case 1:
                                try
                                {
                                    ob.addNewProduct();
                                }
                                catch(Exception E)
                                {
                                }
                                break;
                            case 2:
                                try
                                {
                                    ob.viewAllProduct();
                                }
                                catch(Exception E)
                                {
                                }

                                break;
                            case 3:
                                try
                                {
                                    ob.removeProduct();
                                }
                                catch(Exception E)
                                {
                                }
                        }
                        System.out.println("                                                                           ");
                        System.out.println("                                                                           ");
                        System.out.println("                                                                           ");
                        System.out.println("                                                                           ");
                        System.out.println("Press Enter key to continue...");
                        sc.nextLine();sc.nextLine();
                        break;
                    case 2:
                        ob.clearscreen();
                        System.out.println("                                                                           ");
                        System.out.println("                                                                           ");
                        System.out.println("                                                                           ");
                        System.out.println("                                                                           ");
                        System.out.println("                                                                           ");
                        System.out.println("                                                                           ");
                        System.out.println("                                                                           ");
                        System.out.println("                                                                           ");
                        System.out.println("                                                                           ");
                        System.out.println("\t\t---------------------------------------------------");
                        System.out.println("\t\t\tEnter\n\t\t\t1. Add product Sale Detail\n      \t\t\t2. View Product Sale Detail\n      \t\t\t3. Update Product Sale Detail" );
                        System.out.println("\t\t---------------------------------------------------");
                        System.out.println("Enter your choice :");
                        int ch2=sc.nextInt();
                        ob.clearscreen();
                        switch(ch2)
                        {
                            case 1:
                                try
                                {
                                    ob.addProductSaleDetail();
                                }
                                catch(Exception E)
                                {
                                }
                                break;
                            case 2:
                                try
                                {
                                    ob.viewProductSale();
                                }
                                catch(Exception E)
                                {
                                }

                                break;
                            case 3:
                                try
                                {
                                    ob.updateProductSale();
                                }
                                catch(Exception E)
                                {
                                }
                        }
                        System.out.println("                                                                           ");
                        System.out.println("                                                                           ");
                        System.out.println("                                                                           ");
                        System.out.println("                                                                           ");
                        System.out.println("Press Enter key to continue...");
                        sc.nextLine();sc.nextLine();
                        break;
                    case 3:
                        ob.clearscreen();
                        System.out.println("                                                                           ");
                        System.out.println("                                                                           ");
                        System.out.println("                                                                           ");
                        System.out.println("                                                                           ");
                        System.out.println("                                                                           ");
                        System.out.println("                                                                           ");
                        System.out.println("                                                                           ");
                        System.out.println("                                                                           ");
                        System.out.println("                                                                           ");
                        System.out.println("\t\t---------------------------------------------------");
                        System.out.println("\t\t\tEnter\n\t\t\t1. View  Updated Product Stock" );
                        System.out.println("\t\t---------------------------------------------------");
                        System.out.println("Enter your choice :");
                        int ch3=sc.nextInt();
                        ob.clearscreen();
                        switch(ch3)
                        {
                            case 1:
                                try
                                {
                                    ob.viewProductStock();
                                }
                                catch(Exception E)
                                {
                                }
                        }
                        System.out.println("                                                                           ");
                        System.out.println("                                                                           ");
                        System.out.println("                                                                           ");
                        System.out.println("                                                                           ");
                        System.out.println("Press Enter key to continue...");
                        sc.nextLine();sc.nextLine();
                        break;
                    case 4:
                        ob.clearscreen();
                        try
                        {
                            ob.viewProfitDetails();
                        }
                        catch(Exception E)
                        {
                        }
                        System.out.println("Press Enter key to continue...");
                        sc.nextLine();sc.nextLine();
                        break;

                    case 5:
                        System.exit(0);

                }
            }}
        else
        {
            System.out.println("                                                                           ");
            System.out.println("                                                                           ");
            System.out.println("                                                                           ");
            System.out.println("                                                                           ");
            System.out.println("                                                                           ");
            System.out.println("                                                                           ");
            System.out.println("\t\t\t\tINVALID USER");
        }		}
}
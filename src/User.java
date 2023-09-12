import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
public class User {
    private long ID;
    private int password;
    private String name;
    private long balance;
    private String persianName;;

    public User(long ID){
        this.ID = ID;
    }
    public User(long ID, int password){
        this.ID = ID;
        this.password = password;
    }
    public User(long ID, int password, String name, long balance, String persianName){
        this.ID = ID;
        this.password = password;
        this.name = name;
        this.balance = balance;
        this.persianName = persianName;
    }

    public String getPersianName() {
        return persianName;
    }

    public void setPersianName(String persianName) {
        this.persianName = persianName;
    }

    public void setPassword(int password) {
        this.password = password;
    } ///////////////////////////tedad bayad 4 bashe

    public int getPassword() {
        return password;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance){
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public boolean login(User[] users, int counter){
        for(int i = 0; i < counter; i++){
            if(users[i].getPassword() == this.password && users[i].getID() == this.ID){
                return true;
            }
        }
        return false;
    }

    public String checkBank(long id, int language){
        int check = (int) (id / 1000);
        switch (language) {
            case 1:
                switch (check) {
                    case 589210:
                        return "Sepah Bank";
                    case 603799:
                        return "Melli Bank";
                    case 627648:
                        return "Saderat Bank";
                    case 601770:
                        return "Keshavarzi Bank";
                    case 628023:
                        return "Maskan Bank";
                    case 627760:
                        return "Post Bank";
                    case 622106:
                        return "Parsian Bank";
                    case 610433:
                        return "Mellat Bank";
                    case 627353:
                        return "Tejarat Bank";
                    default:
                        return "Invalid Bank";
                }
            case 2:
                switch (check) {
                    case 589210:
                        return "بانک سپه";
                    case 603799:
                        return "بانک  ملی";
                    case 627648:
                        return "بانک صادرات";
                    case 601770:
                        return "بانک کشاورزس";
                    case 628023:
                        return "بانک مسکن";
                    case 627760:
                        return "پست بانک";
                    case 622106:
                        return "بانک پارسیان";
                    case 610433:
                        return "یانک ملت";
                    case 627353:
                        return "بانک تجارت";
                    default:
                        return "بانک نامعتبر";
                }
            default: return "Not invalid";
        }
    }

    public int findIndex(User[] users, int counter){
        for(int i = 0; i < counter; i++) {
            if (users[i].getPassword() == this.password && users[i].getID() == this.ID) {
                return i;
            }
        }
        return -1;
    }

    public long balance(User[] users, int index, int counter){
        for(int i = 0; i < counter; i++){
            if(users[i].getPassword() == this.password && users[i].getID() == this.ID){
                return users[i].getBalance();
            }
        }
        return -1;
    }

    public int findTargetIndex(User[] users, int counter){
        for(int i = 0; i < counter; i++) {
            if (users[i].getID() == this.ID) {
                return i;
            }
        }
        return -1;
    }

    public String toString(){
        return ID+","+password+","+name+","+balance+","+persianName;
    }

    public void trialFee(User[] user,int counter, int index, String fileAddress){
        user[index].setBalance(user[index].getBalance() - 500);
        try (FileWriter fw = new FileWriter(fileAddress)) {
            for(int i = 0; i < counter; i++){
                fw.write(user[i].toString() + "\n");
            }
            fw.close();
        }catch (IOException io){
            System.out.println(io.getMessage());
        }
    }

    public void transferMoney(User[] user,int counter, int index1, String fileAddress, int index2, long money){
        user[index2].setBalance(user[index2].getBalance() + money);
        user[index1].setBalance(user[index1].getBalance() - money - 1000);
        try (FileWriter fw = new FileWriter(fileAddress)) {
            for(int i = 0; i < counter; i++){
                fw.write(user[i].toString() + "\n");
            }
            fw.close();
        }catch (IOException io){
            System.out.println(io.getMessage());
        }

    }

    public void withdrawalCash(User[] user,int counter, int index, String fileAddress, long money){
        user[index].setBalance(user[index].getBalance() - money);
        try (FileWriter fw = new FileWriter(fileAddress)) {
            for(int i = 0; i < counter; i++){
                fw.write(user[i].toString() + "\n");
            }
            fw.close();
        }catch (IOException io){
            System.out.println(io.getMessage());
        }
    }

    public void charityReceipt(User[] users, int index, int charityID, long money, int language){
        if(language == 1){
            System.out.println("\n\n******************************");
            System.out.println("         ATM RECEIPT\n");
            System.out.println("Date\t\tTime");
            System.out.println(java.time.LocalDate.now() + "\t"+ java.time.LocalTime.now()+ "\n");
            System.out.println("Customer Card : XXXXXX"+ (int)(users[index].getID() % 1000));
            if(charityID == 603799115)
                System.out.println("Amount of Money transfered to \"Mahak charity\" : " + money);
            else if(charityID == 603799116)
                System.out.println("Amount of Money transfered to \"Komite Emam khomeini charity\" : " + money);
            System.out.println("Availability : "+ users[index].getBalance());
            System.out.println("******************************\n\n");
        }else{
            System.out.println("\n\n******************************");
            System.out.println("         رسید دستگاه خودپرداز\n");
            System.out.println("تاریخ\t\t\tزمان");
            System.out.println(java.time.LocalDate.now() + "\t"+ java.time.LocalTime.now()+ "\n");
            System.out.println("شماره کارت : XXXXXX"+ (int)(ID % 1000));
            if(charityID == 603799115)
                System.out.println(" مبلغ انتقال داده شده به خیریه محک : " + money);
            else if(charityID == 603799116)
                System.out.println(" مبلغ انتقال داده شده به خیریه کمیته امام خمینی : " + money);
            System.out.println("مانده حساب : "+ users[index].getBalance());
            System.out.println("******************************\n\n");
        }
    }

    public void bankReceiptCash(User[] users, int index, long money, int language ){
        if(language == 1){
            System.out.println("\n\n******************************");
            System.out.println("         ATM RECEIPT\n");
            System.out.println("Date\t\tTime");
            System.out.println(java.time.LocalDate.now() + "\t"+ java.time.LocalTime.now()+ "\n");
            System.out.println("Customer Card : XXXXXX"+ (int)(users[index].getID() % 1000));
            System.out.println("Amount : " + money);
            System.out.println("Availability : "+ users[index].getBalance());
            System.out.println("******************************\n\n");
        }
        else {
            System.out.println("\n\n******************************");
            System.out.println("         رسید دستگاه خودپرداز\n");
            System.out.println("تاریخ\t\t\tزمان");
            System.out.println(java.time.LocalDate.now() + "\t"+ java.time.LocalTime.now()+ "\n");
            System.out.println("شماره کارت : XXXXXX"+ (int)(users[index].getID() % 1000));
            System.out.println("برداشت : " + money);
            System.out.println("مانده : "+ users[index].getBalance());
            System.out.println("******************************\n\n");
        }
    }

    public void bankReceiptBalance(User[] users, int index, int language ){
        if(language == 1){
            System.out.println("\n\n******************************");
            System.out.println("         ATM RECEIPT\n");
            System.out.println("Date\t\tTime");
            System.out.println(java.time.LocalDate.now() + "\t"+ java.time.LocalTime.now()+ "\n");
            System.out.println("Customer Card : XXXXXX"+ (int)(users[index].getID() % 1000));
            System.out.println("-500T trial fee");
            System.out.println("Availability : "+ users[index].getBalance());
            System.out.println("******************************\n\n");
        }
        else {
            System.out.println("\n\n******************************");
            System.out.println("         رسید دستگاه خودپرداز\n");
            System.out.println("تاریخ\t\t\tزمان");
            System.out.println(java.time.LocalDate.now() + "\t"+ java.time.LocalTime.now()+ "\n");
            System.out.println("شماره کارت : XXXXXX"+ (int)(users[index].getID() % 1000));
            System.out.println("-500 کارمزد");
            System.out.println("مانده : "+ users[index].getBalance());////////////////////////balance khali bezar
            System.out.println("******************************\n\n");
        }
    }

    public void bankReceiptTransfer(User[] users, int index,int targetIndex, long transferMoney, int language ){
        if(language == 1){
            System.out.println("\n\n******************************");
            System.out.println("         ATM RECEIPT\n");
            System.out.println("Date\t\tTime");
            System.out.println(java.time.LocalDate.now() + "\t"+ java.time.LocalTime.now()+ "\n");
            System.out.println("Customer Card : XXXXXX"+ (int)(users[index].getID() % 1000));
            System.out.println("Target Card : XXXXXX"+ (int)(users[targetIndex].getID() % 1000));
            System.out.println("Transfer --> Amount : " + transferMoney);
            System.out.println("Availability : "+ users[index].getBalance());
            System.out.println("******************************\n\n");
        }
        else {
            System.out.println("\n\n******************************");
            System.out.println("         رسید دستگاه خودپرداز\n");
            System.out.println("تاریخ\t\t\tزمان");
            System.out.println(java.time.LocalDate.now() + "\t"+ java.time.LocalTime.now()+ "\n");
            System.out.println("شماره کارت مبدا : XXXXXX"+ (int)(users[index].getID() % 1000));
            System.out.println("شماره کارت مقصد : XXXXXX"+ (int)(users[targetIndex].getID() % 1000));
            System.out.println("انتقال وجه --> مبلغ : " + transferMoney);
            System.out.println("مانده : "+ users[index].getBalance());
            System.out.println("******************************\n\n");
        }
    }


    public void buyCharge(User users[],int counter, int index, int operator, int charge, String fileAddress, int language, long chargeAmount5){
        User iransel = new User(603799113);
        int indexIransel = iransel.findTargetIndex(users, counter);
        User hamrahAval = new User(603799113);
        int indexHamrahAval = hamrahAval.findTargetIndex(users, counter);

        int enoughMoney = 1;
        if(operator == 1){
          //operator iransel
            if(charge == 1){
                if(users[index].getBalance() < 1000){
                    if(language == 1)
                        System.out.println("You have not enough balance");
                    else
                        System.out.println("موجودی کافی نمی باشد");
                    enoughMoney = 0;
                }
                else{
                    users[index].setBalance(users[index].getBalance() - 1000);
                    users[indexIransel].setBalance(users[index].getBalance() + 1000);
                }

            }else if(charge == 2){
                if(users[index].getBalance() < 2000){
                    if(language == 1)
                        System.out.println("You have not enough balance");
                    else
                        System.out.println("موجودی کافی نمی باشد");
                    enoughMoney = 0;
                }
                else{
                    users[index].setBalance(users[index].getBalance() - 2000);
                    users[indexIransel].setBalance(users[index].getBalance() + 2000);
                }

            }else if(charge == 3){
                if(users[index].getBalance() < 5000){
                    if(language == 1)
                        System.out.println("You have not enough balance");
                    else
                        System.out.println("موجودی کافی نمی باشد");
                    enoughMoney = 0;
                }
                else{
                    users[index].setBalance(users[index].getBalance() - 5000);
                    users[indexIransel].setBalance(users[index].getBalance() + 5000);
                }

            }else if(charge == 4){
                if(users[index].getBalance() < 10000){
                    if(language == 1)
                        System.out.println("You have not enough balance");
                    else
                        System.out.println("موجودی کافی نمی باشد");
                    enoughMoney = 0;
                }
                else{
                    users[index].setBalance(users[index].getBalance() - 10000);
                    users[indexIransel].setBalance(users[index].getBalance() + 10000);
                }
            }else if(charge == 5){
                if(users[index].getBalance() < chargeAmount5){
                    if(language == 1)
                        System.out.println("You have not enough balance");
                    else
                        System.out.println("موجودی کافی نمی باشد");
                    enoughMoney = 0;
                }
                else{
                    users[index].setBalance(users[index].getBalance() - chargeAmount5);
                    users[indexIransel].setBalance(users[index].getBalance() + chargeAmount5);
                }
            }
        }else {
            //operator hamrah aval
            if (charge == 1) {
                if (users[index].getBalance() < 1000) {
                    if (language == 1)
                        System.out.println("You have not enough balance");
                    else
                        System.out.println("موجودی کافی نمی باشد");
                    enoughMoney = 0;
                } else {
                    users[index].setBalance(users[index].getBalance() - 1000);
                    users[indexHamrahAval].setBalance(users[index].getBalance() + 1000);
                }
            } else if (charge == 2) {
                if (users[index].getBalance() < 2000) {
                    if (language == 1)
                        System.out.println("You have not enough balance");
                    else
                        System.out.println("موجودی کافی نمی باشد");
                    enoughMoney = 0;
                } else {
                    users[index].setBalance(users[index].getBalance() - 2000);
                    users[indexHamrahAval].setBalance(users[index].getBalance() + 2000);
                }
            } else if (charge == 3) {
                if (users[index].getBalance() < 5000) {
                    if (language == 1)
                        System.out.println("You have not enough balance");
                    else
                        System.out.println("موجودی کافی نمی باشد");
                    enoughMoney = 0;
                } else {
                    users[index].setBalance(users[index].getBalance() - 5000);
                    users[indexHamrahAval].setBalance(users[index].getBalance() + 5000);
                }

            } else if (charge == 4) {
                if (users[index].getBalance() < 10000) {
                    if (language == 1)
                        System.out.println("You have not enough balance");
                    else
                        System.out.println("موجودی کافی نمی باشد");
                    enoughMoney = 0;
                } else {
                    users[index].setBalance(users[index].getBalance() - 10000);
                    users[indexHamrahAval].setBalance(users[index].getBalance() + 10000);
                }
            } else if (charge == 5) {
                if (users[index].getBalance() < chargeAmount5) {
                    if (language == 1)
                        System.out.println("You have not enough balance");
                    else
                        System.out.println("موجودی کافی نمی باشد");
                    enoughMoney = 0;
                } else {
                    users[index].setBalance(users[index].getBalance() - chargeAmount5);
                    users[indexHamrahAval].setBalance(users[index].getBalance() + chargeAmount5);
                }
            }
        }
        if(enoughMoney == 1){
            try (FileWriter fw = new FileWriter(fileAddress)) {
                for(int i = 0; i < counter; i++){
                    fw.write(users[i].toString() + "\n");
                }
                fw.close();
            }catch (IOException io){
                System.out.println(io.getMessage());
            }
            long randNumber =  1000000 + new Random().nextInt(9000000);
            System.out.println("******************");
            if(language == 1)
                 System.out.println("PIN : " + randNumber);
            else
                System.out.println("رمز : " + randNumber);
            System.out.println("\n"+"******************");
        }

    }
}


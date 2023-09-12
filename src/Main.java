import javax.sound.midi.Soundbank;
import java.io.*;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        ///chossing languafe Per or Eng
        int language = 0;
        Scanner scanner = new Scanner(System.in);
        do{
            System.out.println("\t\t\"Choose your language\"");
            System.out.println("1.English\n2.Persian(فارسی)");
            System.out.print("Enter number of the option : ");
            language = scanner.nextInt();
            if(!(language == 1 || language == 2)){
                System.out.println("Invalid option");
            }
        } while(!(language == 1 || language == 2));


        //////////////////////////////////////////read database from file
        User[] userList = new User[100];
        int counter = 0;
        File file = new File("src\\BankInfo.txt");
        try{
            Scanner fileInput = new Scanner(file);
            while (counter < 20){
                String newUser = fileInput.nextLine();
                System.out.println(newUser);
                String[] splitNewUser = newUser.split(",");
                System.out.println();
                userList[counter] = new User(Long.valueOf(splitNewUser[0].trim()), Integer.valueOf(splitNewUser[1].trim()), splitNewUser[2].trim(), Long.valueOf(splitNewUser[3].trim()), splitNewUser[4].trim());
                counter++;
            }

            /////////////////////////////////////choosing language   && login
            long ID = 0;
            int password = 0;
            boolean valid = false;
            int wrongUse = 0;
            if(language == 1){
                System.out.println("Enter The ID card : ");
            }
            else if(language == 2){
                System.out.println("شماره کارت خود را وارد کنید ");
            }
            ID = scanner.nextInt();
            do{

                if(language == 1){
                    System.out.println("Enter 0  to Exit:\nEnter the password :\n");
                }
                else if(language == 2){
                    System.out.println("رمز کارت خود را وارد کنید ");
                }
                password = scanner.nextInt();
                if(password == 0){
                    System.exit(1);
                }
                User yourAccount = new User(ID,password);
                valid = yourAccount.login(userList,counter);
                if(!valid){
                    if(language == 1)
                        System.out.println("Password is wrong");
                    else if(language == 2)
                        System.out.println("رمز ئارد شده صحیح نمی باشد");
                }
                wrongUse++;
            }while (!valid && wrongUse < 3);
            if(wrongUse == 3){
                if(language == 1)
                    System.out.println("You entered password wrong more than 3 times.");
                else
                    System.out.println("رمز بیشتر از 3بار اشتباه وارد شده. ");
                System. exit(1);
            }
            else{
                int option = 0;
                do{
                    User yourAccount = new User(ID, password);
                    if(language == 1){
                        System.out.println("\t\t  <<MENU>>");
                        System.out.println("*******Choose one of options********");
                        System.out.print("1.Balance Inquiry(terminal fee)\t\t2.Withdrawal Cash\n3.Transfer Money(terminal fee)\t\t4.Additional Options\n5.Exit\n");
                    }
                    if(language == 2){
                        System.out.println("\t\t  <<منوی اصلی>>");
                        System.out.println("*******یکی از گزینه ها را انتخاب کنید********");
                        System.out.println("1.موجودی حساب(با کارمزد)\t\t2.دریافت وجه\n3.انتقال وجه(با کارمزد)\t\t4.سایر\n5.خروج");
                    }
                    option = scanner.nextInt();
                    int index = yourAccount.findIndex(userList,counter);

                    ////////////////////////check mojoudi
                    if(option == 1){
                        if(userList[index].getBalance() < 500){
                            if(language == 1)
                                System.out.println("You have not enough balance to see.");
                            else if(language == 2)
                                System.out.println("موجودی حساب شما برای دیدن موجودی حساب کافی نیست");
                        }else {
                            if(language == 1){
                                System.out.printf("Balance : %d\n",userList[index].getBalance());
                                System.out.println("Your balance will decrease 500T (trial fee)");
                            }
                            else if(language == 2){
                                System.out.printf("موجودی حساب : %d\n",userList[index].getBalance());
                                System.out.println("از موجودی حساب شما مباغ 500تومان به عنوان کارمزد کسر خواهد شد");
                            }
                            userList[index].trialFee(userList, counter, index, "src\\BankInfo.txt");
                            int yN = 0;
                            do{
                                if(language == 1){
                                    System.out.println("Do you want receive a receipt?");
                                    System.out.println("1.Yes\t2.No");
                                }
                                else {
                                    System.out.println("آیا مایل به دریافت رسید هستید؟");
                                    System.out.println("1.بلی\t2.خیر");
                                }

                                yN = scanner.nextInt();
                                if(yN == 1){
                                    yourAccount.bankReceiptBalance(userList, index, language);
                                }
                                else if(yN == 2){
                                }
                                else{
                                    if(language == 1)
                                        System.out.println("Invalid option");
                                    else if(language == 2)
                                        System.out.println("گزینه وارد شده نامعتبر است");
                                }
                            }while(!(yN == 1 || yN == 2));
                        }
                    }
                    ////////////////////////////////////bardasht vajh
                    else if(option == 2){
                        long cash = 0;
                        int ok = 0;
                        do{
                            if(language == 1){
                                System.out.println("Enter the amount:");
                            }else  if(language == 2){

                                System.out.println("مبلغ را وارد کنید :");
                            }
                            cash = scanner.nextLong();
                            if(cash % 1000 == 0 && cash > 0 && cash <= 500000){
                                ok = 1;
                            }
                            else{
                                if(language == 1){
                                    System.out.println("Amount of money should be *1000 and more than 0T and less than 500000T");
                                }else{
                                    System.out.println("مبلغ باید *1000 و بیشتر از 0تومان و کمتر از 500000تومان باشد");
                                }
                            }
                        }while(ok == 0);
                        if(userList[index].getBalance() < cash){
                            if(language == 1){
                                System.out.println("\"You have not enough balance");
                            }else  if(language == 2){
                                System.out.println("موجودی حساب شما برای انتثال وجه کافی نمی باشد");
                            }
                        }else{
                            yourAccount.withdrawalCash(userList, counter, index, "src\\BankInfo.txt", cash);
                            if(language == 1){

                                int yesOrNO = 0;
                                do{
                                    System.out.println("Do you want receive a receipt?");
                                    System.out.println("1.Yes\t2.No");
                                     yesOrNO = scanner.nextInt();
                                     if(yesOrNO == 1){
                                         yourAccount.bankReceiptCash(userList, index, cash, language);
                                         System.out.println("SUPPOSE YOU RECEIVE CASH :) \nPlease take your card and cash");
                                         System. exit(1);
                                     }
                                     else if(yesOrNO == 2){
                                         System.out.println("SUPPOSE YOU RECEIVE CASH :) \nPlease take your card and cash");
                                         System. exit(1);
                                     }
                                     else {
                                         System.out.println("Invalid option");
                                     }
                                }while(!(yesOrNO == 1 || yesOrNO == 2));

                            }else{

                                int yesOrNO = 0;
                                do{
                                    System.out.println("آیا مایل به دریافت رسید هستید؟");
                                    System.out.println("1.بلی\t2.خیر");
                                    yesOrNO = scanner.nextInt();
                                    if(yesOrNO == 1){
                                        yourAccount.bankReceiptCash(userList, index, cash, language);
                                        System.out.println("تصور کنید وجه در اختیار شما قرار گرفت :) \nلطفا کارت و وجه خود را بردارید");
                                        System. exit(1);
                                    }else if(yesOrNO == 2){
                                        System.out.println("تصور کنید وجه در اختیار شما قرار گرفت :) \nلطفا کارت و وجه خود را بردارید");
                                        System. exit(1);
                                    }
                                    else{
                                        System.out.println("گزینه وارد شده معتبر نمی باشد");
                                    }
                                }while(!(yesOrNO == 1 || yesOrNO == 2));
                            }
                        }
                    }
                    //////////////////////////////////////////enteghal vajh
                    else if(option == 3){
                        //////taghir
                        if(language == 1){
                            System.out.println("Enter ID number of Target Card");
                        }else  if(language == 2){
                            System.out.println("شماره کارت مقضدد را وارد کنید");
                        }
                        long targetID = scanner.nextLong();
                        User targetAccount = new User(targetID);
                        int targetAccountIndex = targetAccount.findTargetIndex(userList, counter);
                        while(targetAccountIndex == -1){

                            int yesOrNo = 0;
                            do{
                                if(language == 1)
                                    System.out.println("ID card is not valid\nDo you want try again?\n1.Yes\t\t2.No");
                                 else
                                    System.out.println("شماره کارت وارد شده اشتباه است\nآیا می خواهید دوباره تلاش کنید؟\n1.بلی\t\t2.خیر");
                                yesOrNo = scanner.nextInt();
                                if(yesOrNo == 1){
                                    if(language == 1){
                                        System.out.println("Enter ID number of Target Card");
                                    }else  if(language == 2){
                                        System.out.println("شماره کارت مقصد را وارد کنید");
                                    }
                                     targetID = scanner.nextLong();
                                     targetAccount = new User(targetID);
                                     targetAccountIndex = targetAccount.findTargetIndex(userList, counter);
                                }
                                else if(yesOrNo == 2){
                                    targetAccountIndex = -2;
                                }else{
                                    if(language == 1)
                                        System.out.println("Invalid option");
                                    else
                                        System.out.println("گزینه وارد شده نامعتیر است");
                                }
                            }while (!(yesOrNo == 1 || yesOrNo == 2));
                        }if(targetAccountIndex != -1 && targetAccountIndex != -2) {
                            int ok = 0;
                            long transferMoney = 0;
                            do{
                                if(language == 1){
                                    System.out.println("Enter the amount:");
                                }else  if(language == 2){

                                    System.out.println("مبلغ را وارد کنید :");
                                }
                                transferMoney = scanner.nextLong();
                                if(transferMoney % 1000 == 0 && transferMoney > 0 && transferMoney <= 1000000){
                                    ok = 1;
                                }
                                else{
                                    if(language == 1){
                                        System.out.println("Amount of money should be *1000 and more than 0T and less than 1000000T");
                                    }else{
                                        System.out.println("مبلغ باید *1000 و بیشتر از 0تومان و کمتر از 1000000تومان باشد");
                                    }
                                }
                            }while(ok == 0);
                            if(userList[index].getBalance() < transferMoney - 500){
                                if(language == 1){
                                    System.out.println("You have not enough balance to transfer");
                                }else  if(language == 2){
                                    System.out.println("موجودی حساب شما برای انتثال وجه کافی نمی باشد");
                                }
                            }else{
                                if(language == 1){
                                    System.out.printf("Do you want to transfer %dt to \"%s\" from \"%s\"?\n", transferMoney, userList[targetAccountIndex].getName(), targetAccount.checkBank(targetID, 1));
                                }
                                else {
                                    System.out.print(" آیا میخواهید ");
                                    System.out.print(transferMoney);
                                    System.out.print("تومان به حساب ");
                                    System.out.print(userList[targetAccountIndex].getPersianName());
                                    System.out.print(" از ");
                                    String bankName = targetAccount.checkBank(targetID, 2);
                                    System.out.print(bankName);
                                    System.out.println(" انتقال دهید؟");
                                }
                                int yesOrNo = 0;
                                do{
                                    if(language == 1)
                                        System.out.println("1.Yes\t2.No");
                                    else
                                        System.out.println("1.بلی\t2.خیر");
                                    yesOrNo = scanner.nextInt();
                                    if(yesOrNo == 1){
                                        yourAccount.transferMoney(userList, counter, index, "src\\BankInfo.txt", targetAccountIndex, transferMoney);
                                        if(language == 1){
                                            System.out.println("Your balance will decrease 500T (trial fee)");
                                        }
                                        else {
                                            System.out.println("از موجودی حساب شما مباغ 500تومان به عنوان کارمزد کسر خواهد شد");
                                        }
                                        int yN = 0;
                                        do{
                                            if(language == 1){
                                                System.out.println("Do you want receive a receipt?");
                                                System.out.println("1.Yes\t2.No");
                                            }
                                            else {
                                                System.out.println("آیا مایل به دریافت رسید هستید؟");
                                                System.out.println("1.بلی\t2.خیر");
                                            }
                                            yN = scanner.nextInt();
                                            if(yN == 1){
                                                yourAccount.bankReceiptTransfer(userList, index, targetAccountIndex, transferMoney, language);
                                            }
                                            else if(yN == 2){
                                            }
                                            else{
                                                if(language == 1)
                                                    System.out.println("Invalid option");
                                                else if(language == 2)
                                                    System.out.println("گزینه وارد شده نامعتبر است");
                                            }
                                        }while(!(yN == 1 || yN == 2));

                                    }else if(yesOrNo == 2){
                                    }else{
                                        if(language == 1)
                                            System.out.println("Invalid option");
                                        else
                                            System.out.println("گزینه وارد شده نامفتبر می باشد");
                                    }
                                }while (!(yesOrNo == 1 || yesOrNo == 2));
                            }
                        }
                    }
                    else if(option == 4){ ///////////////////////////other options
                            int op = 0;
                            do{
                                if(language == 1)
                                    System.out.println("Choose one of options :\n1.Charity\t\t2.charge\n3.Main menu\t\t4.Exit\n");
                                else if(language == 2)
                                    System.out.println("یکی از گزینه ها را انتخاب کنید :\n1.خیریه\t\t2.شارژ\n3.منوی اصلی\t\t4.خروج\n");
                                op = scanner.nextInt();
                                if(op == 1){
                                    int opCharity = 0;
                                    do{
                                        if(language == 1)
                                            System.out.println("Choose one of options :\n1.Mahak\t\t2.Komite Emam khomeini\n3.Main menu\t\t4.Exit");
                                        else
                                            System.out.println("یکی از گزینه ها را انتخاب کنید :\n1.خیریه محک\t\t2.کمیته امام خمینی\n3.منوی اصلی\t\t4.خروج");

                                        opCharity = scanner.nextInt();

                                        if(opCharity == 1 || opCharity == 2){
                                            long amountToCharity = 0;
                                            int ok = 0;
                                            do{
                                                if(language == 1){
                                                    System.out.println("Enter the amount:");
                                                }
                                                else if(language == 2){
                                                    System.out.println("مبلغ را وارد کنید :");
                                                }
                                                amountToCharity = scanner.nextLong();
                                                if(amountToCharity >= 1000 && amountToCharity <= 1000000){
                                                    ok = 1;
                                                }
                                                else{
                                                    if(language == 1)
                                                        System.out.println("Amount of money should be more than 1000T and less than 1000000T");
                                                    else if(language == 2)
                                                        System.out.println("مبلغ وارد شده باید بیشتر از 1000تومان و کمتر از 1000000تومان باشد");
                                                }
                                            }while(ok == 0);
                                            if(userList[index].getBalance() < amountToCharity){
                                                if(language == 1)
                                                    System.out.println("You have not enough balance");
                                                else if(language == 2)
                                                    System.out.println("موجودی حساب شما کافی نمی باشد");
                                                op = 5;///////////////////////////////////////////////////////////////////////////////////chera ino gozashtam
                                            } else{
                                                int yesOrNo = 0;
                                                do{
                                                    if(language == 1){
                                                        System.out.printf("Do you want to transfer %dt to charity?\n", amountToCharity);
                                                        System.out.println("1.Yes\t2.No");
                                                    }else if(language == 2){
                                                        System.out.println("آیا می خواهید مبلغ" + amountToCharity +"تومان به خیریه انتقال دهید؟");
                                                        System.out.println("1.بلی\t2.خیر");
                                                    }
                                                    yesOrNo = scanner.nextInt();
                                                    User mahak = new User(603799115);
                                                    User komite = new User(603799115);
                                                    int indexMahak = mahak.findTargetIndex(userList, counter);
                                                    int indexKomite = komite.findTargetIndex(userList, counter);
                                                    if(yesOrNo == 1){
                                                        if(opCharity == 1)
                                                            yourAccount.transferMoney(userList, counter, index,"src\\BankInfo.txt", indexMahak, amountToCharity);
                                                        else
                                                            yourAccount.transferMoney(userList, counter, index,"src\\BankInfo.txt", indexKomite, amountToCharity);
                                                        int yN = 0;
                                                        do {
                                                            if (language == 1){
                                                                System.out.println("Do you want receive a receipt?");
                                                                System.out.println("1.Yes\t2.No");
                                                            }
                                                            else if(language == 2){
                                                                System.out.println("آیا مایل به دریافت رسید هستید؟؟");
                                                                System.out.println("1.بلی\t2.خیر");
                                                            }
                                                            yN = scanner.nextInt();
                                                            if(yN == 1){
                                                                if(opCharity == 1)
                                                                    yourAccount.charityReceipt(userList, index,603799115, amountToCharity, language);
                                                                else
                                                                    yourAccount.charityReceipt(userList, index,603799116, amountToCharity, language);
                                                            }
                                                            else if(yN == 2){
                                                            }
                                                            else{
                                                                if(language == 1)
                                                                    System.out.println("Invalid option");
                                                                else if(language == 2)
                                                                    System.out.println("گزینه وارد شده نامعتبر است");
                                                            }
                                                        }while(!(yN == 1 || yN == 2));
                                                    }else if(yesOrNo == 2){
                                                    }else{
                                                        if(language == 1)
                                                            System.out.println("Invalid option");
                                                        else if(language == 2)
                                                            System.out.println("گزینه وارد شده نامعتبر است");
                                                    }
                                                }while (!(yesOrNo == 1 || yesOrNo == 2));
                                            }
                                        }else if(opCharity == 3){
                                           ///Do nothing --> Go main menu
                                        }else if(opCharity == 4){
                                            System. exit(1);
                                        }else {
                                            if(language == 1)
                                                System.out.println("Invalid option");
                                            else if(language == 2)
                                                System.out.println("گزینه وارد شده نامعتبر است");
                                        }
                                    }while (!(opCharity == 1 || opCharity == 2 || opCharity == 3 || opCharity == 4));
                                } else if(op == 2){
                                    /////////////////sharzh
                                    if(language == 1)
                                        System.out.println("Choose one of options :\n1.Iransel\t\t2.Hamrah Aval\n3.Main menu\t\t4.Exit");
                                    else if(language == 2)
                                        System.out.println("یکی از گزینه ها را انتخاب کنید :\n1.ایرانسل\t\t2.همراه اول\n3.منوی اصلی\t\t4.خروج");

                                    int operator = 0;
                                    do{
                                        operator = scanner.nextInt();
                                        if(operator == 1 || operator == 2){
                                            int charge = 0;
                                            do{
                                                if(language == 1)
                                                    System.out.println("Choose on of options :\n1.1000\t\t2.2000\n3.5000\t\t4.10000\n5.others\t\t6.Main menu");
                                                else if(language == 2)
                                                    System.out.println("یکی از گزینه ها را انتخاب کنید :\n1.1000\t\t2.2000\n3.5000\t\t4.10000\n5.سایر مبالغ\t\t6.منوی اصلی");

                                                charge = scanner.nextInt();
                                                if(charge == 1 || charge == 2 || charge == 3 || charge == 4){
                                                    yourAccount.buyCharge(userList, counter, index, operator, charge, "src\\BankInfo.txt", language, 0);
                                                }else if(charge == 5){
                                                    long chargeAmoount = 0;
                                                    int ok = 0;
                                                    do{
                                                        if(language == 1)
                                                            System.out.println("Enter the amount:");
                                                        else if(language == 2)
                                                            System.out.println("مبلغ شارژ را وارد کنید :");

                                                        chargeAmoount = scanner.nextLong();
                                                        if(chargeAmoount % 1000 == 0 && chargeAmoount > 0 && chargeAmoount <= 500000){
                                                            ok = 1;
                                                        }else{
                                                            if(language == 2)
                                                                System.out.println("مقدار شارژ باید بیشتز از 0تومان و کمتر از 500000تومان و *1000 باشد");
                                                            else if(language == 1)
                                                                System.out.println("Amount of charge should be *1000 and more than 0T and less than 500000T");
                                                        }
                                                    }while(ok == 0);
                                                    yourAccount.buyCharge(userList, counter, index, operator, charge, "src\\BankInfo.txt", language, chargeAmoount);
                                                }else if(charge == 6){
                                                    System.exit(1);
                                                }else {
                                                    if(language == 1)
                                                        System.out.println("Invalid option");
                                                    else if(language == 2)
                                                        System.out.println("گزینه وارد شده نامعتبر است");
                                                }
                                            }while (!(charge == 0 || charge == 1 || charge == 2 || charge == 3 || charge == 4 || charge == 5 || charge == 6 ));
                                        }else if(operator == 3){
                                            //Do nothing --> Go main menu
                                        }else if(operator == 4){
                                            System.exit(1);
                                        }else{
                                            if(language == 1)
                                                System.out.println("Invalid option");
                                            else if(language == 2)
                                                System.out.println("گزینه وارد شده نامعتبر است");
                                        }
                                    }while (!(operator == 1 || operator == 2 || operator == 3 || operator == 4));
                                }else if(op == 3){
                                    ///////do nothing
                                }else if(op == 4){
                                    System. exit(1);
                                }else{
                                    if(language == 1)
                                        System.out.println("Invalid option");
                                    else if(language == 2)
                                        System.out.println("گزینه وارد شده نامعتبر است");
                                }
                            }while (!(op == 1 || op == 2 || op == 3 || op == 4));

                    } else if( option != 5){
                        //////////////////az option 1 v 4 nist
                        if(language == 1)
                            System.out.println("Invalid option");
                        else if(language == 2)
                            System.out.println("گزینه وارد شده نامعتبر است");
                    }
                }while(option != 5);
            }
        }catch (FileNotFoundException e){
            System.out.println("File address is not correct");
        }
    }

}

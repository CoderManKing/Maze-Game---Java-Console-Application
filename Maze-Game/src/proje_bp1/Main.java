
import java.util.Scanner;
import java.util.Random;
/**
 * @file Main.java
 * @description Labirent oyunumuz, oyunda birçok bonus ve engel bulunmaktadır ve kullanıcının hedefi az adımla bitişe ulaşmaktır. 
 * @author Muhammed Emin Öz muhammedemin.oz@stu.fsm.edu.tr
 */
public class Main {

    static int hamle_sayisi = 0; //hamle sayisi
    static int teleport_bonus = 0; // T bonusu (x, y) gibi biyere ışılandırılacak.
    static int remove_bonus = 0; // R bonusu
    static int mayınF_çöz_bonus = 0; //F bonusu
    static int hamle_azalt_bonus = 0; //H bonusu
    static int[] anlık_konum = new int[2]; //start noktası sabit olduğu için direk tanımladım.
    static String[] temp = {"w", "a", "s", "d"};
    static int mayin_sayisi = 10;
    static int f_bonus_sayisi = 5;
    static int r_bonus_sayisi = 5;
    static int t_bonus_sayisi = 5;
    static int h_bonus_sayisi = 5;
    static int ceza_bonus_hamle_sayisi = 0;

    public static void main(String[] args) {
        char[][] labirent = {
            {'#', '!', '.', '.', 'R', '.', '.', '.', '.', '.', '.', '#', '.', '.', '.'},
            {'.', '.', '#', '.', '.', '.', '#', '.', 'H', '.', '.', '.', '.', '.', '!'},
            {'F', '.', '.', '.', '#', '.', '!', '.', '.', 'R', '.', '.', '#', '#', '.'},
            {'.', '.', '#', '.', '.', '#', '.', '.', '.', '.', 'F', '.', '.', '.', '.'},
            {'.', '!', '.', '.', '#', '.', '#', '.', '#', '.', '.', '#', '.', '.', '.'},
            {'.', '.', 'H', '.', '.', '!', '.', '.', 'H', '.', '.', 'F', '.', '.', 'R'},
            {'#', '#', '#', '#', '.', '.', '#', '.', '.', '.', 'T', '.', '.', '.', 'E'},
            {'.', '.', '#', '.', 'F', '.', '#', '#', '.', '#', '#', '#', '#', '.', '.'},
            {'.', '#', '.', '.', '.', '.', '!', '.', '#', '.', '.', '.', '#', '.', '.'},
            {'.', 'T', 'T', '.', '#', '#', '.', '.', '.', '.', 'T', '.', '.', '.', 'R'},
            {'.', '.', '.', '#', '.', '.', '.', '#', '.', '#', '.', '#', '.', 'T', '.'},
            {'B', '.', '#', '.', '.', '!', '.', '!', '.', '.', '.', '.', '.', '.', '#'},
            {'.', '.', '.', 'F', '!', '.', '.', '.', 'H', '.', '.', 'R', '.', '.', '.'},
            {'.', '.', 'H', '.', '.', '.', '!', '.', '.', '.', '#', '.', '.', '#', '.'},
            {'.', '.', '.', '#', '.', '.', '#', '.', '#', '.', '#', '.', '.', '#', '#'}};

        int[] start = new int[2]; //2 kapasiteli bir dizide start noktasını tutmak için yer aç
        int[] end = new int[2];  //2 kapasiteli bir dizide end noktasını tutmak için yer aç
        //alttaki for döngüsü start ve end noktalarını bulup önceden açılan diziye yerlerini yazar
        for (int i = 0; i < labirent.length; i++) {
            for (int j = 0; j < labirent[i].length; j++) {
                if (labirent[i][j] == 'B') {
                    start[0] = i;
                    start[1] = j;
                }
                if (labirent[i][j] == 'E') {
                    end[0] = i;
                    end[1] = j;
                }
            }
        }
        
        anlık_konum[0] = start[0];
        anlık_konum[1] = start[1];
        
        System.out.println("Labirent oyununa hoşgeldin! ");
        printTwoDArray(labirent); // labirentin ilk yazdırıldığı yer.
        System.out.println("Adım sayısı: " + hamle_sayisi); // başlangıçta hamle sayısı yazdır.
        System.out.print("Bulunduğunuz konum: ");
        printCoordinate(anlık_konum);
        System.out.print("Bitiş koordinatı: ");
        printCoordinate(end); //player bilgi mesajı       

        Scanner input = new Scanner(System.in); // scanner sınıfı aktif

        while (true) { // labirent oyunumuz bu while döngüsü kırılmadığı sürece çalışacaktır..
            if (anlık_konum[0] == end[0] && anlık_konum[1] == end[1]) { // döngüye girdim eğer bitiş noktası ise oyunu bitirdim.
                System.out.println("Gidilen nokta ‘E’ karakterine eşit. Çıkışa geldiniz. Oyun bitti, tebrikler!\n"
                        + "Toplam adım sayısı: " + hamle_sayisi);
                break;
            } else { // eğer oyun bitmediyse input alınarak devam edilir...
                System.out.println("W, A, S, D karakterlerinden birini giriniz ya da bonus kullanmak için + \n"
                         + "karakterine basınız. Çıkış için “exit” yazınız."); // ya hareket eder, ya bonus kullanır, yada exit yazıp çıkar...
                String user = input.nextLine(); // alırız ve ne seçtiyse ona göre devam ederiz... 
                if (user.contains("+")) { //BONUSLARIN KULLANILABİLDİĞİ YER
                    while (true) {
                        if (isThereBonus() == false) { //sadece T ve H bonusları manuel olarak kullanılabildiğinden onların olup olmadığını kontrol eder...
                            System.out.println("Kullanılabilir bonusunuz bulunamadı! ");// eğer yoksa budur.
                            break;                            
                        } else if (isThereBonus() == true){
                            System.out.println("Kullanmak istediğin bonusu seç (T, H): "); // diğerlerini oyuncu kullanamaz otomatik sistem kullanır.
                            ShowBonus();
                            String chosen_bonus = input.nextLine(); // 
                            char chosen = chosen_bonus.charAt(0);
                            if (chosen == 'T') { // T bonusu kullanılırsa olacaklar...
                                while (true) { // T bonusu döngüsü...
                                    if (teleport_bonus >= 1) {
                                        int[] user_tp_crdnt = new int[2];
                                        System.out.println("Lütfen tp atmak istediğiniz yerin koordinatını giriniz: (x, y) ");
                                        user_tp_crdnt[0] = input.nextInt();
                                        user_tp_crdnt[1] = input.nextInt();
                                        if (labirent[user_tp_crdnt[0]][user_tp_crdnt[1]] == '#' || labirent[user_tp_crdnt[0]][user_tp_crdnt[1]] == '!') {
                                            System.out.println("Işınlanmak istediğiniz yerde mayın yada duvar var, tekrar deneyin. ");
                                        } else {
                                            anlık_konum[0] = user_tp_crdnt[0];
                                            anlık_konum[1] = user_tp_crdnt[1];
                                            if (labirent[anlık_konum[0]][anlık_konum[1]] == 'T') {
                                                System.out.println("'T' karakteri ile karşılaşıldı. Bonus listesine eklendi.");
                                                t_bonus_sayisi--;
                                                teleport_bonus++;
                                            } else if (labirent[anlık_konum[0]][anlık_konum[1]] == 'H') {
                                                System.out.println("'H' karakteri ile karşılaşıldı. Bonus listesine eklendi.");
                                                h_bonus_sayisi--;
                                                hamle_azalt_bonus++;
                                            } else if (labirent[anlık_konum[0]][anlık_konum[1]] == 'F') {
                                                System.out.println("'F' karakteri ile karşılaşıldı. Bonus listesine eklendi.");
                                                f_bonus_sayisi--;
                                                mayınF_çöz_bonus++;
                                            } else if (labirent[anlık_konum[0]][anlık_konum[1]] == 'R') {
                                                System.out.println("'R' karakteri ile karşılaşıldı. Bonus listesine eklendi.");
                                                r_bonus_sayisi--;
                                                remove_bonus++;
                                            }
                                            break;
                                        }
                                    } else if (teleport_bonus == 0) {
                                        System.out.println("T bonusu dizi içinde yok yeniden bonus giriniz: ");
                                        break;
                                    }                                   
                                }                            
                            } else if (chosen == 'H') { // H bonusu kullanırlırsa olacaklar...
                                while (true) {
                                    if (hamle_azalt_bonus >= 1) {
                                        if (hamle_sayisi >= 2) {
                                            hamle_azalt_bonus--;
                                            ceza_bonus_hamle_sayisi -= 2;
                                            System.out.println("Hamle sayısı 2 azaltıldı. ");
                                        } else {
                                            System.out.println("Hamle sayısı 0'ın altına düşemez!!! ");
                                        }
                                    } else if (hamle_azalt_bonus == 0) {
                                        System.out.println("H bonusu dizi içinde yok yeniden bonus giriniz: ");
                                    }
                                    break;
                                }
                            }
                            break;    // bonus döngüsünden çıkarır
                        }
                    }
                    System.out.println("Bonus döngüsünden çıkıldı. ");                     
                } else if (user.toLowerCase().equals("exit")) { // exit yazarsa oyundan çıkılır...
                    System.out.println("Oyundan çıktınız... ");
                    break;
                } else if (user.length() != 1) { // "ws" , "awd" gibi inputların engellenmesi işlemi gerçekleşir.
                    System.out.println("Lütfen sadece tek bir tuşa bas!!! ");
                } else if (isInclude(temp) && user.length() == 1) { // W A S D den birisi seçildiyse yani ilerlenmek istendiyse olacaklar... (ve tek karakter olmalı)

                    int temprow = anlık_konum[0]; // hareketten önceki konumlar tutulur..
                    int tempcol = anlık_konum[1]; // hareketten önceki konumlar tutulur..
                    moving(user.charAt(0)); //hamle sayisi wasd de artar.harita dışına çıkma önlenir.

                    if (labirent[anlık_konum[0]][anlık_konum[1]] == '#') { //DUVARA GELİRSEK OLACAKLAR!!!
//                        hamle_sayisi++;
                        if (remove_bonus >= 1) { // H bonusu varsa otomatik kullanılır.
                            remove_bonus--;
                            System.out.println("Remove bonusu etkinleşti, duvar kalktı ilerlendi.");
                        } else if (remove_bonus == 0) {
                            anlık_konum[0] = temprow; //ilerleyemediğimizden dolayı anlık konumu geri alır...
                            anlık_konum[1] = tempcol; // ...
                            System.out.println("Duvar var ilerleyemezsiniz!!!");
                        }
                    } else if (labirent[anlık_konum[0]][anlık_konum[1]] == '!') { //MAYINA GELİRSEK OLACAKLAR!!!
                        mayin_sayisi--; // shuffle işleminde mayın sayısını "1" eksilt.
//                        hamle_sayisi++;
                        if (mayınF_çöz_bonus >= 1) {
                            mayınF_çöz_bonus--;
                            labirent[anlık_konum[0]][anlık_konum[1]] = '.'; //bulunulan yer noktayla değişir
                            System.out.println("Mayına gelindi ama sorun yok joker kullanıldı devam edebilirsin bol şans...");
                        } else if (mayınF_çöz_bonus == 0) {
                            ceza_bonus_hamle_sayisi += 5;
                            labirent[anlık_konum[0]][anlık_konum[1]] = '.'; //raporda istenen gibi mayına basılırsa '.' ile değiştirilir. (Engeller-ii.-2.madde)
                            System.out.println("Mayın patladı!.. Hamle sayınız 5 arttı...");
                        }
                    } else if (labirent[anlık_konum[0]][anlık_konum[1]] != '#' && labirent[anlık_konum[0]][anlık_konum[1]] != '!') { // duvar yada mayın değilse
//                        hamle_sayisi++;
                        if (labirent[anlık_konum[0]][anlık_konum[1]] == 'T') {
                            System.out.println("'T' karakteri ile karşılaşıldı. Bonus listesine eklendi.");
                            t_bonus_sayisi--;
                            teleport_bonus++;
                        } else if (labirent[anlık_konum[0]][anlık_konum[1]] == 'H') {
                            System.out.println("'H' karakteri ile karşılaşıldı. Bonus listesine eklendi.");
                            h_bonus_sayisi--;
                            hamle_azalt_bonus++;
                        } else if (labirent[anlık_konum[0]][anlık_konum[1]] == 'F') {
                            System.out.println("'F' karakteri ile karşılaşıldı. Bonus listesine eklendi.");
                            f_bonus_sayisi--;
                            mayınF_çöz_bonus++;
                        } else if (labirent[anlık_konum[0]][anlık_konum[1]] == 'R') {
                            System.out.println("'R' karakteri ile karşılaşıldı. Bonus listesine eklendi.");
                            r_bonus_sayisi--;
                            remove_bonus++;
                        }
                    }
                    labirent[anlık_konum[0]][anlık_konum[1]] = '.'; // üzerinden geçilen yerleri nokta yapar.
                }                
            }
            if(hamle_sayisi % 5 == 0){
                makeMapDot(labirent);
                makeMapMayın(labirent);
                shuffleBonus(labirent);
            }   
            System.out.println("");
            printTwoDArray(labirent);
            System.out.println("Toplam adım: " + hamle_sayisi);
            System.out.print("Anlık konumun: ");
            printCoordinate(anlık_konum);
        } // while döngü sonu
        
       
        hamle_sayisi += ceza_bonus_hamle_sayisi; // bonustan kazandığı yada cezaları en son ekler...
        System.out.println("Toplam adım skorunuz: " + hamle_sayisi);
        //
    }
    
    public static char[][] makeMapDot(char[][] lab) { // duvar hariç heryeri nokta yapar.
        for (int satir = 0; satir < lab.length; satir++) {
            for (int sutun = 0; sutun < lab[satir].length; sutun++) {
                char karakter = lab[satir][sutun];
                if (lab[satir][sutun] != '#') {
                    if(lab[satir][sutun] != 'B' && lab[satir][sutun] != 'E'){
                    lab[satir][sutun] = '.';
                    }
                }
                  
            }
        } 
        return lab;
    }
    
    public static char[][] makeMapMayın(char[][] lab){
        Random random = new Random();
        int temp_mayin = mayin_sayisi;
        int sayi_sinir = mayin_sayisi;
        for(int k = 0; k < mayin_sayisi; k++){
            int rnd_i = random.nextInt(15);
            int rnd_j = random.nextInt(15);
            if(lab[rnd_i][rnd_j] == '#' || lab[rnd_i][rnd_j] == 'B' || lab[rnd_i][rnd_j] == 'E' ){ // mayının duvara dağıtıldığı esnada sayı kaybetmemesi için
                k--;
            } else if(lab[rnd_i][rnd_j] != '#'){
                if(lab[rnd_i][rnd_j] != 'B' && lab[rnd_i][rnd_j] != 'E'){
                lab[rnd_i][rnd_j] = '!';
                temp_mayin--;
                }
                if(temp_mayin == 0){
                    break;                
                }
            }
                
        }      
        return lab;
    }
    
    public static char[][] shuffleBonus(char[][] lab) {
        Random random = new Random();
        int temp_r = r_bonus_sayisi;
        int temp_f = f_bonus_sayisi;
        int temp_t = t_bonus_sayisi;
        int temp_h = h_bonus_sayisi;

        for (int k = 0; k < r_bonus_sayisi; k++) {
            int rnd_i = random.nextInt(15);
            int rnd_j = random.nextInt(15);
            if (lab[rnd_i][rnd_j] != '.') { // bonusun duvara dağıtıldığı esnada sayı kaybetmemesi için
                k--;
            } else if (lab[rnd_i][rnd_j] == '.') {           
                lab[rnd_i][rnd_j] = 'R';
                temp_r--;
                if (temp_r == 0) {
                    break;
                }
            }
        } for (int k = 0; k < f_bonus_sayisi; k++) {
            int rnd_i = random.nextInt(15);
            int rnd_j = random.nextInt(15);
            if (lab[rnd_i][rnd_j] != '.') { // bonusun duvara dağıtıldığı esnada sayı kaybetmemesi için
                k--;
            } else if (lab[rnd_i][rnd_j] == '.') {
                lab[rnd_i][rnd_j] = 'F';
                temp_f--;
                if (temp_f == 0) {
                    break;
                }
            }
        } for (int k = 0; k < t_bonus_sayisi; k++) {
            int rnd_i = random.nextInt(15);
            int rnd_j = random.nextInt(15);
            if (lab[rnd_i][rnd_j] != '.') { // bonusun duvara dağıtıldığı esnada sayı kaybetmemesi için
                k--;
            } else if (lab[rnd_i][rnd_j] == '.') {
                lab[rnd_i][rnd_j] = 'T';
                temp_t--;
                if (temp_t == 0) {
                    break;
                }
            }
        } for (int k = 0; k < h_bonus_sayisi; k++) {
            int rnd_i = random.nextInt(15);
            int rnd_j = random.nextInt(15);
            if (lab[rnd_i][rnd_j] != '.') { // bonusun duvara dağıtıldığı esnada sayı kaybetmemesi için
                k--;
            } else if (lab[rnd_i][rnd_j] == '.') {
                lab[rnd_i][rnd_j] = 'H';
                temp_h--;
                if (temp_h == 0) {
                    break;
                }
            }
        }
        return lab;
    }
    
    // oyuncunun manuel kullanabileceği (T & H) bonusların olup olmadığına bakar...
    public static boolean isThereBonus() {
        boolean result = false; // default false
        if (teleport_bonus >= 1 || hamle_azalt_bonus >= 1) { // varsa true yapar
            result = true;
        }
//        if () { // varsa true yapar
//            result = true;
//        }
        return result;
    }

    // hareketle ilgili komutları yönetir ve yaptırır...
    public static void moving(char hareket) { // ve harita dışına çıkmayı engeller...
        int templaterow;
        int templatecol;
        templaterow = anlık_konum[0];
        templatecol = anlık_konum[1];
        switch (hareket) {
            case 'w', 'W' -> {
                hamle_sayisi++;
                anlık_konum[0]--;
                System.out.print("Yukarı gittiniz ve bulunduğunuz konum: ");
                printCoordinate(anlık_konum);
            }
            case 'a', 'A' -> {
                hamle_sayisi++;
                anlık_konum[1]--;
                System.out.print("Sola gittiniz ve bulunduğunuz konum: ");
                printCoordinate(anlık_konum);
            }
            case 's', 'S' -> {
                hamle_sayisi++;
                anlık_konum[0]++;
                System.out.print("Aşağı gittiniz ve bulunduğunuz konum: ");
                printCoordinate(anlık_konum);
            }
            case 'd', 'D' -> {
                hamle_sayisi++;
                anlık_konum[1]++;
                System.out.print("Sağa gittiniz ve bulunduğunuz konum: ");
                printCoordinate(anlık_konum);
            }
            default -> System.out.println("Lütfen W A S D karakterlerinden birini giriniz! ");
        }
        // haritanın dışına taştıysa gidemezsin yazdırır ve anlık konumu harita içine geri sokar
        if (anlık_konum[0] > 14 || anlık_konum[0] < 0 || anlık_konum[1] > 14 || anlık_konum[1] < 0) {
            System.out.println("Harita dışına çıkamazsınız!!! \n Lütfen başka yöne gidin. ");
            anlık_konum[0] = templaterow;
            anlık_konum[1] = templatecol;
        }
    }
    
    public static boolean isInclude(String[] dizi) {
        boolean result = false;
        for (int x = 0; x < dizi.length; x++) {
            if (dizi[x].equals("w") || dizi[x].equals("a") || dizi[x].equals("s") || dizi[x].equals("d")) {
                result = true;
            }
        }
        return result;
    }

    public static void ShowBonus() {
        System.out.println("Teleport Bonusu (T): " + teleport_bonus);
        System.out.println("Hamle Azaltma Bonusu (H): " + hamle_azalt_bonus);
    }

    public static void printCoordinate(int[] array) {
        System.out.println("("+array[0]+", "+ array[1] + ")\n"); // ve altta bir satır boşluk bırakır.\n
    }

    // İki boyutlu diziyi "for" döngüsü ile yazdır
    public static void printTwoDArray(char[][] twoDArray) {
        for (char[] twoDArray1 : twoDArray) {
            for (int j = 0; j < twoDArray1.length; j++) {
                System.out.print(twoDArray1[j] + " ");
            }
            System.out.println();// labirenti satır satır böler alt satıra geçerek.
        }
        System.out.println(); // labirenti yazınca bir satır boşluk bırakır estetik görüntü için
    }
}

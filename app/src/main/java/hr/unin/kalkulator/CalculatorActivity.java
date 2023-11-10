package hr.unin.kalkulator;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Scanner;

public class CalculatorActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);


        Scanner scanner = new Scanner(System.in);

        System.out.println("Unesite prvi broj:");
        double broj1 = scanner.nextDouble();

        System.out.println("Unesite drugi broj:");
        double broj2 = scanner.nextDouble();

        // Odabir operacije
        System.out.println("Odaberite operaciju (+, -, *, /):");
        char operacija = scanner.next().charAt(0);

        Operacija izabranaOperacija = null;

        switch (operacija) {
            case '+':
                izabranaOperacija = new Zbrajanje();
                break;
            case '-':
                izabranaOperacija = new Oduzimanje();
                break;
            case '*':
                izabranaOperacija = new Mnozenje();
                break;
            case '/':
                izabranaOperacija = new Dijeljenje();
                break;
            default:
                System.out.println("Nepoznata operacija");
                System.exit(1);
        }

        double rezultat = izabranaOperacija.izracunaj(broj1, broj2);
        System.out.println("Rezultat: " + rezultat);
    }
    // Operacija.java
    public abstract class Operacija {
        public abstract double izracunaj(double broj1, double broj2);
    }
    // Zbrajanje.java
    public class Zbrajanje extends Operacija {
        @Override
        public double izracunaj(double broj1, double broj2) {
            return broj1 + broj2;
        }
    }
    // Oduzimanje.java
    public class Oduzimanje extends Operacija {
        @Override
        public double izracunaj(double broj1, double broj2) {
            return broj1 - broj2;
        }
    }
    // Mnozenje.java
    public class Mnozenje extends Operacija {
        @Override
        public double izracunaj(double broj1, double broj2) {
            return broj1 * broj2;
        }
    }
    // Dijeljenje.java
    public class Dijeljenje extends Operacija {
        @Override
        public double izracunaj(double broj1, double broj2) {
            if (broj2 != 0) {
                return broj1 / broj2;
            } else {
                throw new ArithmeticException("Dijeljenje s nulom nije dozvoljeno");
            }
        }
    }

}

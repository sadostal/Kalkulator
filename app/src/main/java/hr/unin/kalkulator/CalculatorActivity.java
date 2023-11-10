package hr.unin.kalkulator;

import static java.lang.Double.parseDouble;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.nio.charset.StandardCharsets;

public class CalculatorActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private double broj1;
    private double broj2;
    private double rezultat;
    private char operacija;
    Operacija izabranaOperacija;
    private EditText unosPrviOperand;
    private EditText unosDrugiOperand;
    private Spinner spinnerOperation;
    private Button buttonIzracunaj;
    private TextView textViewResult;

    //Implementacije listener-a za spinner
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String buffer = (String) adapterView.getItemAtPosition(i);
        operacija = (char) buffer.getBytes(StandardCharsets.UTF_8)[0];
        System.out.println(operacija);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        operacija = '+';
        System.out.println(operacija);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        //Instanciranje komponenata

        unosPrviOperand = findViewById(R.id.unosPrviOperand);
        unosDrugiOperand = findViewById(R.id.unosDrugiOperand);
        spinnerOperation = findViewById(R.id.spinnerOperation);
        buttonIzracunaj = findViewById(R.id.buttonCalculate);
        textViewResult = findViewById(R.id.textViewResult);

        //Dodavanje opcija u spinner iz res

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.operatori,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerOperation.setAdapter(adapter);

        //Vezivanje event-ova
        unosPrviOperand.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                String buffer = String.valueOf(unosPrviOperand.getText()).isEmpty() ?
                        "0":
                        String.valueOf(unosPrviOperand.getText());
                broj1 = parseDouble(buffer);
                System.out.println(broj1);
            }
        });

        unosDrugiOperand.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                String buffer = String.valueOf(unosDrugiOperand.getText()).isEmpty() ?
                        "0":
                        String.valueOf(unosDrugiOperand.getText());
                broj2 = parseDouble(buffer);
                System.out.println(broj2);
            }
        });
        spinnerOperation.setOnItemSelectedListener(this);
        buttonIzracunaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doMath();
            }
        });
    }


    protected void doMath(){
        try {
        switch (operacija) {
            case '+':
                this.izabranaOperacija = new Zbrajanje();
                break;
            case '-':
                this.izabranaOperacija = new Oduzimanje();
                break;
            case '*':
                this.izabranaOperacija = new Mnozenje();
                break;
            case '/':
                this.izabranaOperacija = new Dijeljenje();
                break;
            default:
                throw new ArithmeticException("Nepoznata operacija!");
                //System.exit(1);
        }
        if (this.izabranaOperacija != null)
        rezultat = this.izabranaOperacija.izracunaj(broj1, broj2);
        System.out.println(rezultat);
        displayResult();
        } catch (Exception e){
            System.out.println("Greška: " + e.getMessage());
        }

    }

    protected void displayResult(){
        textViewResult.setText(((Double)rezultat).toString());
    }
    // Zbrajanje.java
    public class Zbrajanje implements Operacija {
        public double izracunaj(double broj1, double broj2) {
            return broj1 + broj2;
        }
    }
    // Oduzimanje.java
    public class Oduzimanje implements Operacija {
        public double izracunaj(double broj1, double broj2) {
            return broj1 - broj2;
        }
    }
    // Mnozenje.java
    public class Mnozenje implements Operacija {
        public double izracunaj(double broj1, double broj2) {
            return broj1 * broj2;
        }
    }
    // Dijeljenje.java
    public class Dijeljenje implements Operacija {
        public double izracunaj(double broj1, double broj2) throws ArithmeticException {
            if (broj2 != 0) {
                return broj1 / broj2;
            } else {
                throw new ArithmeticException("Dijeljenje s nulom nije dozvoljeno");
            }
        }
    }

}

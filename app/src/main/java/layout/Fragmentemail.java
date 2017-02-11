package layout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.myAndroidApp.RemaindMyTask.R;


public class Fragmentemail extends android.app.Fragment {
    Button buttonSend;
    EditText textTo;
    EditText textSubject;
    EditText textMessage;
    String[] CC = {""};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //View view = null;
        View view = inflater.inflate(R.layout.fragment_fragmentemail, container, false);
        buttonSend = (Button) view.findViewById(R.id.buttonSend);
        textTo = (EditText) view.findViewById(R.id.editTextTo);
        textSubject = (EditText) view.findViewById(R.id.editTextSubject);
        textMessage = (EditText) view.findViewById(R.id.editTextmessage);

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String to = textTo.getText().toString();
                String subject = textSubject.getText().toString();
                String message = textMessage.getText().toString();



                 Intent emailIntent = new Intent(Intent.ACTION_SEND);
                 emailIntent.setData(Uri.parse("mailto:"));
                 emailIntent.setType("message/rfc822");
                 emailIntent.putExtra(Intent.EXTRA_EMAIL, to);
                 //new String[]{ to}
                 emailIntent.putExtra(Intent.EXTRA_CC, CC);
                 emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
                // emailIntent.setType("text/plain");
                 emailIntent.putExtra(Intent.EXTRA_TEXT, message);

                 startActivity(Intent.createChooser(emailIntent, "Choose an Email client :"));


            }

        });

        return view;
    }

}

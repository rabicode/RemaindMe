package layout;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.myAndroidApp.RemaindMyTask.R;

import java.util.ArrayList;

public class Fragmentmessage extends android.app.Fragment implements AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {

    // Initialize variables
    AutoCompleteTextView textView=null;
    private ArrayAdapter<String> adapter;
    // Store contacts values in these arraylist
    public static ArrayList<String> phoneValueArr = new ArrayList<String>();
    public static ArrayList<String> nameValueArr = new ArrayList<String>();
    String toNumberValue="";
    String number =" ";
    EditText textSMS;

    /** Called when the activity is first created. */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_fragmentmessage, container, false);
        final Button Send = (Button) view.findViewById(R.id.button);
        // Initialize AutoCompleteTextView values

        textView = (AutoCompleteTextView) view.findViewById(R.id.toNumber);
        textSMS = (EditText) view.findViewById(R.id.editTextSMS);
        //Create adapter
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, new ArrayList<String>());
        textView.setThreshold(1);

        //Set adapter to AutoCompleteTextView
        textView.setAdapter(adapter);
        textView.setOnItemSelectedListener(this);
        textView.setOnItemClickListener(this);

        // Read contact data and add data to ArrayAdapter
        // ArrayAdapter used by AutoCompleteTextView
        readContactData();
        /********** Button Click pass textView object ***********/
        Send.setOnClickListener(BtnAction());
        return view;
    }
    private View.OnClickListener BtnAction() {
        return new View.OnClickListener() {
            public void onClick(View v) {
				/* */
                make_message();
                makePhone_message();
            }

        };
    }

    private void make_message(){

        String message = textSMS.getText().toString();
        String phoneNo = textView.getText().toString();
        if (phoneNo.length() > 0 && message.length() > 0) {
            sendMessage(phoneNo, message);
        }else {
            Toast.makeText(getActivity(),"Please enter phone number and message", Toast.LENGTH_SHORT).show();
        }

    }

    private void sendMessage(String phoneNo,String message) {

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, message, null, null);
            Toast.makeText(getActivity(),"Message sent", Toast.LENGTH_SHORT).show();
        }catch (Exception e) {

            Toast.makeText(getActivity(), "Message fail.Please try again", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void makePhone_message(){
        String message = textSMS.getText().toString();
        if (toNumberValue.length() > 0 && message.length() > 0) {
            send_Message(toNumberValue, message);
        }else {
            Toast.makeText(getActivity(),"Please enter phone number and message", Toast.LENGTH_SHORT).show();
        }
    }

    private void send_Message(String number,String message) {

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(toNumberValue, null, message, null, null);
            Toast.makeText(getActivity(),"Message sent", Toast.LENGTH_SHORT).show();
        }catch (Exception e) {

            Toast.makeText(getActivity(), "Message fail.Please try again", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }



    private void readContactData() {

        try {

            /*********** Reading Contacts Name And Number **********/

            String phoneNumber = "";
            ContentResolver cr = getActivity()
                    .getContentResolver();

            //Query to get contact name

            Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

            // If data data found in contacts
            if (cur.getCount() > 0) {
                Log.i("AutocompleteContacts", "Reading   contacts..");
                int k=0;
                String name = "";
                while (cur.moveToNext())
                {
                    String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                    name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                    //Check contact have phone number
                    if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0)
                    {

                        //Create query to get phone number by contact id
                        Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                                        + " = ?", new String[] { id }, null);
                        int j=0;

                        while (pCur.moveToNext())
                        {
                            // Sometimes get multiple data
                            if(j==0)
                            {
                                // Get Phone number
                                phoneNumber =""+pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                                // Add contacts names to adapter
                                adapter.add(name);

                                // Add ArrayList names to adapter
                                phoneValueArr.add(phoneNumber.toString());
                                nameValueArr.add(name.toString());

                                j++;
                                k++;
                            }
                        }
                        pCur.close();
                    }

                }

            }
            cur.close();


        } catch (Exception e) {
            Log.i("AutocompleteContacts","Exception : "+ e);
        }


    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position,
                               long arg3) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);

    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        // TODO Auto-generated method stub

        // Get Array index value for selected name
        int i = nameValueArr.indexOf(""+arg0.getItemAtPosition(arg2));

        // If name exist in name ArrayList
        if (i >= 0) {

            // Get Phone Number
            toNumberValue = phoneValueArr.get(i);

            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(
                    Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);


        }

    }

    public void onResume() {
        super.onResume();
    }

    public void onDestroy() {
        super.onDestroy();
    }

}
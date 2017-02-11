package layout;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.myAndroidApp.RemaindMyTask.R;

import java.util.ArrayList;


public class FragmentCall extends android.app.Fragment implements AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {

    // Initialize variables
    AutoCompleteTextView textView = null;
    private ArrayAdapter<String> adapter;
    // Store contacts values in these arraylist
    public static ArrayList<String> phoneValueArr = new ArrayList<String>();
    public static ArrayList<String> nameValueArr = new ArrayList<String>();
    String toNumberValue = "";
    String number = " ";


    /** Called when the activity is first created. */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_fragment_call, container, false);

        final Button Send = (Button) view.findViewById(R.id.button);
        // Initialize AutoCompleteTextView values

        textView = (AutoCompleteTextView) view.findViewById(R.id.toNumber);

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
                make_call();
                makePhone_call();
            }

        };
    }

    private void make_call() {
        number = textView.getText().toString();
        //Toast.makeText(getApplicationContext(),"number=" +number,Toast.LENGTH_LONG).show();


        try {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + number));
            startActivity(intent);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getActivity(),
                    "Call failed, please try again later!", Toast.LENGTH_SHORT).show();

        }
    }

    private void makePhone_call() {
        try {
            Intent phoneIntent = new Intent(Intent.ACTION_CALL);
            phoneIntent.setData(Uri.parse("tel:" + toNumberValue));

            startActivity(phoneIntent);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getActivity(),
                    "Call failed, please try again later!", Toast.LENGTH_SHORT).show();

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
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(
                Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);


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

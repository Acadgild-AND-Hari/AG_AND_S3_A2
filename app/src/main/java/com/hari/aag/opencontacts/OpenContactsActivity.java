package com.hari.aag.opencontacts;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OpenContactsActivity extends AppCompatActivity
    implements View.OnClickListener{

    private static final String LOG_TAG = OpenContactsActivity.class.getSimpleName();

    private final int PICK_CONTACT = 101;

    @BindView(R.id.id_contacts) Button openContactsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_contacts);
        ButterKnife.bind(this);

        openContactsBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.id_contacts:
                Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
                        ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(contactPickerIntent, PICK_CONTACT);
                break;
        }
    }

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        if (reqCode == PICK_CONTACT &&
                resultCode == Activity.RESULT_OK) {
            displayContactInfo(data);
        }
    }

    private void displayContactInfo(Intent data){
        Uri contactData = data.getData();
        Cursor c =  managedQuery(contactData, null, null, null, null);
        if (c.moveToFirst()) {
            String name = c.getString(c.getColumnIndex(
                    ContactsContract.Contacts.DISPLAY_NAME));
            Toast.makeText(this, "You have selected - " + name, Toast.LENGTH_SHORT).show();
            Log.d(LOG_TAG, "You have selected - " + name);
        }
    }
}

package com.example.tonar_robots;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class PopupActivity extends AppCompatActivity {

    private Button openInputPopupDialogButton = null;
    // This listview is just under above button.
    private ListView userDataListView = null;
    // Below edittext and button are all exist in the popup dialog view.
    private View popupInputDialogView = null;
    // Contains user name data.
    private EditText articulEdit = null;
    // Contains password data.
    private EditText kolvoEdit = null;

    private EditText timeStartEdit = null;
    private EditText timeEndEdit = null;
    private EditText timeVipEdit = null;

    // Contains email data.
    private EditText passBrigadaEdit = null;
    // Click this button in popup dialog to save user input data in above three edittext.
    private Button saveUserDataButton = null;
    // Click this button to cancel edit user data.
    private Button cancelUserDataButton = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {


        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup);

        initMainActivityControls();







        openInputPopupDialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create a AlertDialog Builder.
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(PopupActivity.this);
                // Set title, icon, can not cancel properties.
                alertDialogBuilder.setTitle("User Data Collection Dialog.");
                alertDialogBuilder.setIcon(R.drawable.ic_launcher_background);
                alertDialogBuilder.setCancelable(false);
                // Init popup dialog view and it's ui controls.
                initPopupViewControls();
                // Set the inflated layout view object to the AlertDialog builder.
                alertDialogBuilder.setView(popupInputDialogView);
                // Create AlertDialog and show.
                final AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                // When user click the save user data button in the popup dialog.
                saveUserDataButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Get user data from popup dialog editeext.
                        String userName = articulEdit.getText().toString();
                        String password = kolvoEdit.getText().toString();
                        String email = passBrigadaEdit.getText().toString();
                        // Create data for the listview.
                        String[] titleArr = { "User Name", "Password", "Email"};
                        String[] dataArr = {userName, password, email};
                        ArrayList<Map<String,Object>> itemDataList = new ArrayList<Map<String,Object>>();;
                        int titleLen = titleArr.length;
                        for(int i =0; i < titleLen; i++) {
                            Map<String,Object> listItemMap = new HashMap<String,Object>();
                            listItemMap.put("title", titleArr[i]);
                            listItemMap.put("data", dataArr[i]);
                            itemDataList.add(listItemMap);
                        }
                        SimpleAdapter simpleAdapter = new SimpleAdapter(PopupActivity.this,itemDataList,android.R.layout.simple_list_item_2,
                                new String[]{"title","data"},new int[]{android.R.id.text1,android.R.id.text2});
                        userDataListView.setAdapter(simpleAdapter);
                        alertDialog.cancel();
                    }
                });
                cancelUserDataButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.cancel();
                    }
                });
            }
        });

    /* Initialize main activity ui controls ( button and listview ). */








        // 1. Success message
        Button buttonSuccess = findViewById(R.id.successButton);


        buttonSuccess.setOnClickListener(new View.OnClickListener() {


            public void onClick(View v) {
                new SweetAlertDialog(PopupActivity.this)
                        .setTitleText("Here's a message!")
                        .show();
            }
        });

        // 2. Confirmation message
        Button buttonWarning = findViewById(R.id.confirmationButton);
        buttonWarning.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new SweetAlertDialog(PopupActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Are you sure?")
                        .setContentText("You won't be able to recover this file!")
                        .setConfirmText("Delete!")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                            }
                        })
                        .setCancelButton("Cancel", new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                            }
                        })
                        .show();
            }
        });

        // 3. Error message
        Button buttonDanger = findViewById(R.id.errorButton);
        buttonDanger.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new SweetAlertDialog(PopupActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText("Something went wrong!")

                        .show();

            }
        });

        // 4. Loading message
        Button buttonLoading = findViewById(R.id.loadingButton);
        buttonLoading.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SweetAlertDialog pDialog = new SweetAlertDialog(PopupActivity.this, SweetAlertDialog.PROGRESS_TYPE);
                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                final EditText input = new EditText(PopupActivity.this);

                input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_CLASS_TEXT);

// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_CLASS_TEXT);
                pDialog.setTitleText("Loading ...");
                pDialog.setCancelable(true);
                pDialog.show();
            }
        });

        // 5. Confirm success
        Button buttonConfirmSuccess = findViewById(R.id.confirmSuccessButton);

        buttonConfirmSuccess.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new SweetAlertDialog(PopupActivity.this, SweetAlertDialog.WARNING_TYPE)

                        .setTitleText("Are you sure?")

                        .setContentText("Won't be able to recover this file!")
                        .setConfirmText("Yes,delete it!")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog
                                        .setTitleText("Deleted!")
                                        .setContentText("Your imaginary file has been deleted!")
                                        .setConfirmText("OK")
                                        .setConfirmClickListener(null)
                                        .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                            }
                        })

                        .show();

            }
        });

    }
    private void initMainActivityControls()
    {
        if(openInputPopupDialogButton == null)
        {
            openInputPopupDialogButton = (Button)findViewById(R.id.button_popup_overlay_input);
        }
        if(userDataListView == null)
        {
            userDataListView = (ListView)findViewById(R.id.listview_user_data);
        }
    }
    /* Initialize popup dialog view and ui controls in the popup dialog. */
    private void initPopupViewControls()
    {
        // Get layout inflater object.
        LayoutInflater layoutInflater = LayoutInflater.from(PopupActivity.this);
        // Inflate the popup dialog from a layout xml file.
        popupInputDialogView = layoutInflater.inflate(R.layout.popup_input_dialog, null);
        // Get user input edittext and button ui controls in the popup dialog.
        articulEdit = (EditText) popupInputDialogView.findViewById(R.id.articul);
        kolvoEdit = (EditText) popupInputDialogView.findViewById(R.id.kolvoVne);
        passBrigadaEdit = (EditText) popupInputDialogView.findViewById(R.id.passBrigada);
        timeStartEdit = (EditText) popupInputDialogView.findViewById(R.id.timeStart);
        timeEndEdit = (EditText) popupInputDialogView.findViewById(R.id.timeEnd);
        timeVipEdit = (EditText) popupInputDialogView.findViewById(R.id.timeVip);
        saveUserDataButton = popupInputDialogView.findViewById(R.id.button_save_user_data);
        cancelUserDataButton = popupInputDialogView.findViewById(R.id.button_cancel_user_data);
        // Display values from the main activity list view in user input edittext.
    }




}

package com.sportx.pk.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;
import com.sportx.pk.Adapters.ConversationArchivedAdapter;
import com.sportx.pk.Misc.Misc;
import com.sportx.pk.Models.Conversation;
import com.sportx.pk.R;
import com.sportx.pk.SharedPref.SharedPref;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ConversationArchived extends Fragment {

    private Context context;
    Misc misc;
    SharedPref sharedPref;
    private ArrayList<Conversation> conversationsListModel;
    ConversationArchivedAdapter conversationsAdapter;
    private RecyclerView view;
    private TextView textView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.conversation_archived, container, false);

        context = getActivity();
        misc = new Misc(context);
        sharedPref = new SharedPref(context);

        conversationsListModel = new ArrayList<>();
        conversationsAdapter = new ConversationArchivedAdapter(context, conversationsListModel);

        textView = rootView.findViewById(R.id.no_ip);

        view = rootView.findViewById(R.id.archived_conversation);
        view.setLayoutManager(new LinearLayoutManager(getActivity()));
        view.setAdapter(conversationsAdapter);

        if(misc.isConnectedToInternet()) {
            inProgressJobs();
        }
        else{
            misc.showToast("No Internet Connection");
        }
        return rootView;
    }

    private void inProgressJobs() {
        final ProgressDialog pd = new ProgressDialog(context);
        pd.setMessage("Please wait... ");
        pd.setCancelable(false);
        pd.show();
        Ion.with(context)
                .load(misc.ROOT_PATH+"conversation/get_conversation_by_email_archived/"+sharedPref.getEmail())
                .asString()
                .withResponse()
                .setCallback(new FutureCallback<Response<String>>() {
                    @Override
                    public void onCompleted(Exception e, Response<String> result) {
                        if(e != null) {
                            misc.showToast("Please check your connection");
                            pd.dismiss();
                            return;
                        }
                        else{
                            try {
                                JSONArray jsonArray = new JSONArray(result.getResult());
                                if(jsonArray.length() < 1) {
                                    textView.setVisibility(View.VISIBLE);
                                    pd.dismiss();
                                    return;
                                }
                                conversationsListModel.clear();
                                for(int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                                    String conversationId = jsonObject.getString("_id");
                                    String customerEmail = jsonObject.getString("customerEmail");
                                    String customerName = jsonObject.getString("customerName");
                                    String customerPicture = jsonObject.getString("customerPic");
                                    String serviceProviderEmail = jsonObject.getString("serviceProviderEmail");
                                    String serviceProviderName = jsonObject.getString("serviceProviderName");
                                    String serviceProviderPicture = jsonObject.getString("serviceProviderPic");
                                    String state = jsonObject.getString("state");
                                    String date = jsonObject.getString("date");
                                    String time = jsonObject.getString("time");
                                    int user_role = sharedPref.getUserRole();

                                    conversationsListModel.add(new Conversation(conversationId, customerEmail, customerName, customerPicture, serviceProviderEmail, serviceProviderName, serviceProviderPicture, state, date,time, user_role));
                                }
                                conversationsAdapter = new ConversationArchivedAdapter(context, conversationsListModel);
                                view.setAdapter(conversationsAdapter);

                                pd.dismiss();
                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }
                            pd.dismiss();
                        }
                    }
                });
    }
}

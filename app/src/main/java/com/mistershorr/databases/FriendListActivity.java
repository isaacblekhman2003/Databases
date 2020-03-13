package com.mistershorr.databases;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.icu.util.Freezable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.UserService;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FriendListActivity extends AppCompatActivity {

    public static final String EXTRA_FRIEND = "friend";
    private ListView friendView;
    List<Friend> friendList;
    FloatingActionButton add;
    List<Friend> sort;
    private FriendAdapter friendAdapter;
    private DataQueryBuilder queryBuilder;
    Friend temp;
    @Override
    protected void onResume() {
        super.onResume();
        if(queryBuilder!=null) {
            method(queryBuilder);
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friendlistactivity);
    final String userId = Backendless.UserService.CurrentUser().getObjectId();

    add = findViewById(R.id.floatingActionButton5);
    String whereClause= "ownerId = '"+ userId + "'";
         queryBuilder= DataQueryBuilder.create();
        queryBuilder.setWhereClause(whereClause);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent targetIntent = new Intent(FriendListActivity.this, FriendDetailActivity.class);
                startActivity(targetIntent);
            }
        });

       method(queryBuilder);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.corona, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case R.id.name_menu_item:

                if(friendList!=null) {
                    for (int i = 0; i < friendList.size() - 1; i++) {
                        for (int j = i + 1; j < friendList.size() - 1; j++) {
                            if ((friendList.get(i).getName().compareTo(friendList.get(j).getName())) > 0) {
                                temp = friendList.get(i);
                                friendList.remove(i);
                                friendList.add(temp);
                            }


                        }
                    }

                    friendAdapter = new FriendAdapter(sort);
                    friendView.setAdapter(friendAdapter);
                    friendAdapter.notifyDataSetChanged();
                }
                return true;
            case R.id.money_menu_item: {


                for (int i = 0; i < friendList.size()-1; i++)
                {
                    for (int j = i + 1; j < friendList.size()-1; j++)
                    {
                        if ((friendList.get(i).getMoneyOwed() > (friendList.get(j).getMoneyOwed())))
                        {
                            temp = friendList.get(i);
                            friendList.remove(i);
                            friendList.add(temp);
                        }


                    }
                }

                friendAdapter = new FriendAdapter(sort);
                friendView.setAdapter(friendAdapter);
                friendAdapter.notifyDataSetChanged();


                return true;
            }
            case R.id.swole_menu_item: {


                for (int i = 0; i < friendList.size()-1; i++)
                {
                    for (int j = i + 1; j < friendList.size()-1; j++)
                    {
                        if ((friendList.get(i).getGymFrequency() > (friendList.get(j).getGymFrequency())))
                        {
                            temp = friendList.get(i);
                            friendList.remove(i);
                            friendList.add(temp);
                        }


                    }
                }

                friendAdapter = new FriendAdapter(sort);
                friendView.setAdapter(friendAdapter);
                friendAdapter.notifyDataSetChanged();


            }

            default:
                return super.onOptionsItemSelected(item);}



    }


    private class FriendAdapter extends ArrayAdapter<Friend> {

        private TextView name;
        private TextView moneyowed;
        private TextView gymfrequency;


        private List<Friend> friendList;

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.friend, parent, false);
            }


            gymfrequency = convertView.findViewById(R.id.textView_friend_GymFrequency);
            name = convertView.findViewById(R.id.textView_friend_name);
            moneyowed = convertView.findViewById(R.id.textView_friend_moneyOwed);




            gymfrequency.setText(friendList.get(position).getGymFrequency()+"");
            name.setText(friendList.get(position).getName());
              moneyowed.setText(friendList.get(position).getMoneyOwed()+"");


            return convertView;


        }



        public FriendAdapter(List<Friend> friendList) {
            super(FriendListActivity.this, -1, friendList);
            this.friendList = friendList;


        }
    }
    public void method(DataQueryBuilder queryBuilder){
        Backendless.Data.of(Friend.class).find(queryBuilder, new  AsyncCallback<List<Friend>>() {
            @Override
            public void handleResponse(List<Friend> response) {

                if(response!=null) {
                    friendList =response;
                    friendView= findViewById(R.id.listView_main_friendlist);
                    friendAdapter = new FriendAdapter(response);
                    friendView.setAdapter(friendAdapter);
                    friendView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            Friend friend = friendList.get(i);
                            Intent targetIntent =  new Intent(FriendListActivity.this, FriendDetailActivity.class);
                            targetIntent.putExtra(EXTRA_FRIEND,friend);
                            startActivity(targetIntent);



                        }
                    });
                }



            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(FriendListActivity.this, fault.getDetail(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

package com.example.openingactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AdminActivity extends AppCompatActivity implements Request {


    //XML elements
    TextView textViewAdminHeader;
    Button button_admin_return, button_admin_delete_user, button_admin_update_user_status, button_admin_force_leaderboard_refresh, button_admin_delete_user_security;
    Button button_admin_update_user_name, button_admin_delete_event, button_admin_delete_playlist, button_admin_ban_user;
    EditText input_admin_modify_user, input_admin_new_status, input_admin_new_name, input_admin_event_id, input_admin_playlist_id;
    ExecutorService executorService;

    /* Commented out for now, may not be used in the future, tbd
    Button button_admin_delete_groupchat;
    EditText input_admin_groupchat_id;
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        //map XML elements to UI elements
        textViewAdminHeader = findViewById(R.id.textViewAdminHeader);
        button_admin_return = findViewById(R.id.button_admin_return);
        button_admin_delete_user = findViewById(R.id.button_admin_delete_user);
        input_admin_modify_user = findViewById(R.id.textInput_admin_modify_user);
        button_admin_update_user_status = findViewById(R.id.button_admin_update_user_status);
        input_admin_new_status = findViewById(R.id.textInput_admin_new_status);
        button_admin_force_leaderboard_refresh = findViewById(R.id.button_admin_force_leaderboard_refresh);
        button_admin_delete_user_security = findViewById(R.id.button_admin_delete_user_security);
        button_admin_update_user_name = findViewById(R.id.button_admin_update_user_name);
        input_admin_new_name = findViewById(R.id.textInput_admin_new_name);
        button_admin_delete_event = findViewById(R.id.button_admin_delete_event);
        input_admin_event_id = findViewById(R.id.textInput_admin_event_id);
        button_admin_delete_playlist = findViewById(R.id.button_admin_delete_playlist);
        input_admin_playlist_id = findViewById(R.id.textInput_admin_playlist_id);
        button_admin_ban_user = findViewById(R.id.button_admin_ban_user);

        /* Commented out for now, may not be used in the future, tbd
        button_admin_delete_groupchat = findViewById(R.id.button_admin_delete_groupchat);
        input_admin_groupchat_id = findViewById(R.id.textInput_admin_groupchat_id);
         */


        executorService = Executors.newSingleThreadExecutor();

        /**
         * This code is for the admin settings back button
         * returns the user to the profile page
         */
        button_admin_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        /**
         * This code is for the admin settings delete user button
         * allows an admin to delete a user from the database
         */
        button_admin_delete_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int ID = Integer.parseInt(input_admin_modify_user.getText().toString());
                    deleteUser(ID);
                }catch (Exception e){
                    System.out.println("Invalid ID");
                }
            }

            /**
             * deletes the user from the database
             */
            private void deleteUser(int userToDeleteID) {
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        //sendDeleteRequest(DEL_URL + "/users/" + user.getUserID()); // old method
                        String result = sendRequest("DELETE", "/users/delete/" + userToDeleteID, null);

                    }
                });
            }
        });

        /**
         * This code is for the admin settings update user status button
         * Allows the admin to update a users status to 1, 2 or 3
         * 1 being general user, 2 being influencer, 3 being admin
         */
        button_admin_update_user_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int ID = Integer.parseInt(input_admin_modify_user.getText().toString());
                    int newStatus = Integer.parseInt(input_admin_new_status.getText().toString());
                    updateUserStatus(ID, newStatus);
                } catch (Exception e) {
                    System.out.println("Invalid ID");
                }
            }

            /**
             * updates the user status in the database
             * @param userID the users id
             * @param newStatus the users new status (1, 2 or 3) all users default to 1.
             */
            private void updateUserStatus(int userID, int newStatus) {

                JSONObject json = new JSONObject();

                try {
                    json.put("accountStatus", newStatus);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        String result = sendRequest("PUT", "/user/edit/" + userID , json.toString());
                        //log the result
                        System.out.println(result);
                    }
                });
            }
        });

        /**
         * This code is for the admin settings force leaderboard refresh button
         * Upon pressing the button, the leaderboard will refresh its values to 0?
         */
        button_admin_force_leaderboard_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forceLeaderboardRefresh();
            }

            /**
             * TODO fix this so it actually refreshes leaderboards
             */
            private void forceLeaderboardRefresh() {
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        String result = sendRequest("GET", "/leaderboard/refresh", null);
                        //log the result
                    }
                });
            }
        });

        /**
         * This code is for the admin settings delete user security button
         * Allows an admin to delete the a users security questions
         */
        button_admin_delete_user_security.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int ID = Integer.parseInt(input_admin_modify_user.getText().toString());
                    deleteUserSecurity(ID);
                }catch (Exception e) {
                    System.out.println("Invalid ID");
                }
            }

            /**
             * deletes the user security from the database
             * @param id user id youd like to delete the security questions from
             *           TODO this may not work with id, current methods work using user email, will have to test and see
             */
            private void deleteUserSecurity(int id) {
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        String result = sendRequest("DELETE", "/forgetPassword/" + id, null);
                        //log the result
                    }
                });
            }
        });

        /**
         * This code is for the admin settings update user name button
         * Allows the admin to update a users name
         */
        button_admin_update_user_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userID = input_admin_modify_user.getText().toString();
                String newUsername = input_admin_new_name.getText().toString();
                updateUsername(newUsername, userID);
            }

            /**
             * updates the user name in the database
             * @param newUsername the new username
             */
            private void updateUsername(String newUsername, String userID) {
                JSONObject json = new JSONObject();

                try {
                    json.put("userName", newUsername);
                } catch (Exception e) {
                    e.printStackTrace();
                }


                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        String result = sendRequest("PUT", "/user/edit/" + userID , json.toString());
                        //log the result
                    }
                });
            }
        });

        /**
         * This code is for the admin settings delete event button
         * Allows the admin to delete an event from the database
         */
        button_admin_delete_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String eventID = input_admin_event_id.getText().toString();
                    deleteEvent(eventID);
                } catch (Exception e) {
                    System.out.println("Invalid ID");
                }
            }

            /**
             * deletes the event from the database
             * @param eventID the event id
             *                TODO have a backend create the method for deleting the event
             */
            private void deleteEvent(String eventID) {
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        String result = sendRequest("DELETE", "/events/" + eventID, null);
                        //log the result
                    }
                });
            }
        });

        /**
         * This code is for the admin settings delete playlist button
         * Allows the admin to delete a playlist from the database
         */
        button_admin_delete_playlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String playlistID = input_admin_playlist_id.getText().toString();
                    deletePlaylist(playlistID);
                } catch (Exception e) {
                    System.out.println("Invalid ID");
                }
            }

            /**
             * deletes the playlist from the database
             * @param playlistID the playlist id
             *
             */
            private void deletePlaylist(String playlistID) {
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        String result = sendRequest("DELETE", "/playlistEntry/deletePlaylist/" + playlistID, null);
                        //log the result
                        System.out.println(result);
                    }
                });
            }
        });

        /**
         * This code is for the admin settings delete groupchat button
         * Allows the admin to delete a groupchat from the database
         */
        /*
        button_admin_delete_groupchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String groupchatID = input_admin_groupchat_id.getText().toString();
                    deleteGroupchat(groupchatID);
                } catch (Exception e) {
                    System.out.println("Invalid ID");
                }
            }

            /**
             * deletes the groupchat from the database
             * @param groupchatID the groupchat id
             *                TODO have a backend create the method for deleting the groupchat

            private void deleteGroupchat(String groupchatID) {
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        String result = sendRequest("DELETE", "/groupchats/delete/" + groupchatID, null);
                        //log the result
                    }
                });
            }
        });
            */


        /**
         * This code is for the admin settings ban user button
         * Allows an admin to ban a user by ID, banning prevents an account from logging in, but does
         * NOT delete the account from the database
         */
        button_admin_ban_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int ID = Integer.parseInt(input_admin_modify_user.getText().toString());
                    banUser(ID);
                } catch (Exception e) {
                    System.out.println("Invalid ID");
                    }
            }

            /**
             * bans the user from the database
             * @param ID of user to ban
             */
            private void banUser(int ID) {
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        String result = sendRequest("PUT", "/users/update/" + ID + "/-1", null);
                        //log the result
                        System.out.println(result);
                    }
                });
            }
        });
    }
}

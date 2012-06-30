/******************************************************************************
 *
 *  Copyright 2011 Tavendo GmbH
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 ******************************************************************************/


/******************************************************************************
 *
 *  Modified By Magnux as an example for IOBahn
 *
 ******************************************************************************/
package com.magnux.iobahn.simpleevents;

import java.util.Date;

import com.magnux.iobahn.SocketIO;
import com.magnux.iobahn.SocketIOConnection;

import com.magnux.iobahn.simpleevents.R;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SimpleEventsActivity extends Activity {

   @SuppressWarnings("unused")
   private static final String TAG = "com.magnux.iobahn.simpleevents";

   private static final String PREFS_NAME = "IOBahnSimpleEvents";

   private SharedPreferences mSettings;

   private static EditText mHostname;
   private static EditText mPort;
   private static TextView mStatusline;
   private static Button mStart;

   private final SocketIO mConnection = new SocketIOConnection();

   private void alert(String message) {
      Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
   }

   private void loadPrefs() {

      mHostname.setText(mSettings.getString("hostname", "10.0.2.2"));
      mPort.setText(mSettings.getString("port", "9000"));
   }

   private void savePrefs() {

      SharedPreferences.Editor editor = mSettings.edit();
      editor.putString("hostname", mHostname.getText().toString());
      editor.putString("port", mPort.getText().toString());
      editor.commit();
   }

   private void setButtonConnect() {
      mStart.setText("Connect");
      mStart.setOnClickListener(new Button.OnClickListener() {
         public void onClick(View v) {
            test();
         }
      });
   }

   private void setButtonDisconnect() {
      mStart.setText("Disconnect");
      mStart.setOnClickListener(new Button.OnClickListener() {
         public void onClick(View v) {
            mConnection.disconnect();
         }
      });
   }

   /**
    * We want PubSub events delivered to us in JSON payload to be automatically
    * converted to this domain POJO. We specify this class later when we subscribe.
    */
   private static class MyEvent {

      public String text;
      public Date date;
      public int millis;
      
      @Override
      public String toString() {
         return "text: " + text +
                "\ndate: " + date + 
                "\nmillis: " + millis+"}";
      }
   }

   private void test() {

      final String wsuri = "ws://" + mHostname.getText() + ":" + mPort.getText();

      mStatusline.setText("Connecting to\n" + wsuri + " ..");

      setButtonDisconnect();

      // we establish a connection by giving the WebSockets URL of the server
      // and the handler for open/close events
      mConnection.connect(wsuri, new SocketIO.ConnectionHandler() {

         @Override
         public void onOpen() {

            // The connection was successfully established. we set the status
            // and save the host/port as Android application preference for next time.
            mStatusline.setText("Connected to\n" + wsuri);
            savePrefs();

            mConnection.on("myevent", MyEvent.class, new SocketIO.EventHandler() {

               @Override
               public void onEvent(String name, Object event) {

                  // when we get an event, we safely can cast to the type we specified previously
                  MyEvent evt = (MyEvent) event;
                  
                  //alert("Event received : " + evt.toString());
                  mStatusline.setText("Event received : " + evt.toString());
                  MyEvent myclientevent = new MyEvent();
                  myclientevent.text = "text sent from the client";
                  myclientevent.date = new Date();
                  mConnection.emit("myclientevent", myclientevent);
               }
            });
         }

         @Override
         public void onClose(int code, String reason) {

            // The connection was closed. Set the status line, show a message box,
            // and set the button to allow to connect again.
            mStatusline.setText("Connection closed.");
            alert(reason);
            setButtonConnect();
         }
      });
   }

   @Override
   public void onCreate(Bundle savedInstanceState) {

      super.onCreate(savedInstanceState);
      setContentView(R.layout.main);

      mHostname = (EditText) findViewById(R.id.hostname);
      mPort = (EditText) findViewById(R.id.port);
      mStatusline = (TextView) findViewById(R.id.statusline);
      mStart = (Button) findViewById(R.id.start);

      mSettings = getSharedPreferences(PREFS_NAME, 0);
      loadPrefs();

      setButtonConnect();
   }
}
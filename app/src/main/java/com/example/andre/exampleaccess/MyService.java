package com.example.andre.exampleaccess;

import java.util.ArrayList;
import java.util.List;
import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityWindowInfo;
import android.view.accessibility.AccessibilityNodeInfo;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MyService extends AccessibilityService {

    static final String TAG = "TAG";

    WindowManager windowManager;
    ImageView back,next,ok,bt_macro,play_macro;
    WindowManager.LayoutParams params;
    TextView focus;
    TextView recording;
    boolean record = false;
    ArrayList<AccessibilityNodeInfo> macro = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();

        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        back = new ImageView(this);
        next = new ImageView(this);
        ok = new ImageView(this);
        bt_macro = new ImageView(this);
        play_macro = new ImageView(this);

        ok.setBackgroundColor(Color.DKGRAY);
        back.setBackgroundColor(Color.DKGRAY);
        next.setBackgroundColor(Color.DKGRAY);
        bt_macro.setBackgroundColor(Color.DKGRAY);
        play_macro.setBackgroundColor(Color.DKGRAY);

        focus = new TextView(this);
        focus.setBackgroundColor(Color.WHITE);
        focus.setTextColor(Color.BLACK);

        recording = new TextView(this);
        recording.setBackgroundColor(Color.WHITE);
        recording.setTextColor(Color.BLACK);
        recording.setText("NO");

        params= new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.TRANSLUCENT);

        params.gravity = Gravity.TOP|Gravity.CENTER;
        params.x = 10;
        params.y = 50;

        windowManager.addView(focus, params);

        params= new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.TRANSLUCENT);

        params.gravity = Gravity.TOP|Gravity.CENTER;
        params.x = 10;
        params.y = 250;

        windowManager.addView(recording, params);

        back.setImageResource(R.drawable.ic_back);
        next.setImageResource(R.drawable.ic_next);
        ok.setImageResource(R.drawable.ic_ok);
        bt_macro.setImageResource(R.drawable.ic_action_name);
        play_macro.setImageResource(R.drawable.ic_play_macro);

        params= new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.TRANSLUCENT);

        params.gravity = Gravity.CENTER_VERTICAL|Gravity.RIGHT;
        params.x = 300;
        params.y = 780;

        windowManager.addView(play_macro, params);

        params= new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.TRANSLUCENT);

        params.gravity = Gravity.CENTER_VERTICAL|Gravity.RIGHT;
        params.x = 700;
        params.y = 780;

        windowManager.addView(bt_macro, params);

        params= new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.TRANSLUCENT);

        params.gravity = Gravity.CENTER_VERTICAL|Gravity.RIGHT;
        params.x = 960;
        params.y = 80;

        windowManager.addView(back, params);

        params= new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.TRANSLUCENT);

        params.gravity = Gravity.CENTER_VERTICAL|Gravity.RIGHT;
        params.x = 380;
        params.y = 80;

        windowManager.addView(next, params);

        params= new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.TRANSLUCENT);

        params.gravity = Gravity.CENTER_VERTICAL|Gravity.RIGHT;
        params.x = 700;
        params.y = 80;

        windowManager.addView(ok, params);

        bt_macro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                record=!record;
                if(record){
                    recording.setText("YES");
                    macro.clear();
                }
                else
                    recording.setText("NO " + macro.size());


            }
        });

        play_macro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(AccessibilityNodeInfo ani : macro) {
                    ani.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    AccessibilityNodeInfo n = findFocus(AccessibilityNodeInfo.FOCUS_INPUT).focusSearch(View.FOCUS_BACKWARD);
                    n.performAction(AccessibilityNodeInfo.FOCUS_INPUT);
                    focus.setText(findFocus(AccessibilityNodeInfo.FOCUS_INPUT) == null ? "null" : findFocus(AccessibilityNodeInfo.FOCUS_INPUT).getText() + "");

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    AccessibilityNodeInfo n = findFocus(AccessibilityNodeInfo.FOCUS_INPUT).focusSearch(View.FOCUS_FORWARD);
                    n.performAction(AccessibilityNodeInfo.FOCUS_INPUT);
                    focus.setText(findFocus(AccessibilityNodeInfo.FOCUS_INPUT) == null ? "null" : findFocus(AccessibilityNodeInfo.FOCUS_INPUT).getText() + "");

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    findFocus(AccessibilityNodeInfo.FOCUS_INPUT).performAction(AccessibilityNodeInfo.ACTION_CLICK);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private String getEventTypeString(int eventType) {
        switch (eventType) {
            case AccessibilityEvent.TYPE_ANNOUNCEMENT:
                return "TYPE_ANNOUNCEMENT";
            case AccessibilityEvent.TYPE_GESTURE_DETECTION_END:
                return "TYPE_GESTURE_DETECTION_END";
            case AccessibilityEvent.TYPE_GESTURE_DETECTION_START:
                return "TYPE_GESTURE_DETECTION_START";
            case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:
                return "TYPE_NOTIFICATION_STATE_CHANGED";
            case AccessibilityEvent.TYPE_TOUCH_EXPLORATION_GESTURE_END:
                return "TYPE_TOUCH_EXPLORATION_GESTURE_END";
            case AccessibilityEvent.TYPE_TOUCH_EXPLORATION_GESTURE_START:
                return "TYPE_TOUCH_EXPLORATION_GESTURE_START";
            case AccessibilityEvent.TYPE_TOUCH_INTERACTION_END:
                return "TYPE_TOUCH_INTERACTION_END";
            case AccessibilityEvent.TYPE_TOUCH_INTERACTION_START:
                return "TYPE_TOUCH_INTERACTION_START";
            case AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUSED:
                return "TYPE_VIEW_ACCESSIBILITY_FOCUSED";
            case AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUS_CLEARED:
                return "TYPE_VIEW_ACCESSIBILITY_FOCUS_CLEARED";
            case AccessibilityEvent.TYPE_VIEW_CLICKED:
                return "TYPE_VIEW_CLICKED";
            case AccessibilityEvent.TYPE_VIEW_FOCUSED:
                return "TYPE_VIEW_FOCUSED";
            case AccessibilityEvent.TYPE_VIEW_HOVER_ENTER:
                return "TYPE_VIEW_HOVER_ENTER";
            case AccessibilityEvent.TYPE_VIEW_HOVER_EXIT:
                return "TYPE_VIEW_HOVER_EXIT";
            case AccessibilityEvent.TYPE_VIEW_LONG_CLICKED:
                return "TYPE_VIEW_LONG_CLICKED";
            case AccessibilityEvent.TYPE_VIEW_SCROLLED:
                return "TYPE_VIEW_SCROLLED";
            case AccessibilityEvent.TYPE_VIEW_SELECTED:
                return "TYPE_VIEW_SELECTED";
            case AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED:
                return "TYPE_VIEW_TEXT_CHANGED";
            case AccessibilityEvent.TYPE_VIEW_TEXT_SELECTION_CHANGED:
                return "TYPE_VIEW_TEXT_SELECTION_CHANGED";
            case AccessibilityEvent.TYPE_VIEW_TEXT_TRAVERSED_AT_MOVEMENT_GRANULARITY:
                return "TYPE_VIEW_TEXT_TRAVERSED_AT_MOVEMENT_GRANULARITY";
            case AccessibilityEvent.TYPE_WINDOWS_CHANGED:
                return "TYPE_WINDOWS_CHANGED";
            case AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED:
                return "TYPE_WINDOW_CONTENT_CHANGED";
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
                return "TYPE_WINDOW_STATE_CHANGED";
        }
        return String.format("unknown (%d)", eventType);
    }

    private String getEventText(AccessibilityEvent event) {
        StringBuilder sb = new StringBuilder();
        for (CharSequence s : event.getText()) {
            sb.append(s);
        }
        return sb.toString();
    }

    private void dumpNode(AccessibilityNodeInfo node, int indent) {

        if (node == null) {
            Log.v(TAG, "node is null (stopping iteration)");
            return;
        }

        //for(AccessibilityNodeInfo.AccessibilityAction a: node.getActionList()){
          //  Log.d("ACTIONS", a.toString());
        //}
      //  Log.d("ACTIONS", "------------------------------------------------");
        String indentStr = new String(new char[indent * 3]).replace('\0', ' ');
        Log.v(TAG, String.format("%s NODE: %s", indentStr, node.toString()));
        Log.v(TAG, String.format("%s TEXT: %s", indentStr, node.getText()));


        for (int i = 0; i < node.getChildCount(); i++) {
            dumpNode(node.getChild(i), indent + 1);
        }
        /* NOTE: Not sure if this is really required. Documentation is unclear. */
        node.recycle();
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        /* Show the accessibility event */
       /* Log.d(TAG, String.format(
                "onAccessibilityEvent: [type] %s [class] %s [package] %s [time] %s [text] %s",
                getEventTypeString(event.getEventType()), event.getClassName(), event.getPackageName(),
                event.getEventTime(), getEventText(event)));

        /* Show all the windows available */
       // if(getEventTypeString(event.getEventType()).equals("TYPE_VIEW_CLICKED"))
         //   Toast.makeText(getApplicationContext(), getEventText(event).equals("")?"Vazio":getEventText(event), Toast.LENGTH_SHORT).show();

        if(getEventTypeString(event.getEventType()).equals("TYPE_VIEW_CLICKED") && record){
           Log.d("Clicked", getEventText(event).equals("")?"Vazio":getEventText(event));

            if(event.getSource()!=null && !getEventText(event).equals(""))
            macro.add(event.getSource());
        }
       if(getEventTypeString(event.getEventType()).equals("TYPE_WINDOW_CONTENT_CHANGED")){
            AccessibilityNodeInfo focus_node=getRootInActiveWindow();
            if(focus_node!=null) {
                focus_node.performAction(AccessibilityNodeInfo.FOCUS_INPUT);
                AccessibilityNodeInfo ani = findFocus(AccessibilityNodeInfo.FOCUS_INPUT);
                focus.setText(ani == null ? "null" : ani.getText() + "");
            }
        }

        /* Dump the view hierarchy */
       // focus_node=getRootInActiveWindow();
        //dumpNode(getRootInActiveWindow(), 0);
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        // Log.v(TAG, "onServiceConnected");
        AccessibilityServiceInfo info = new AccessibilityServiceInfo();
        info.flags = AccessibilityServiceInfo.DEFAULT |
                AccessibilityServiceInfo.FLAG_RETRIEVE_INTERACTIVE_WINDOWS |
                AccessibilityServiceInfo.FLAG_REQUEST_ENHANCED_WEB_ACCESSIBILITY |
                AccessibilityServiceInfo.FLAG_REPORT_VIEW_IDS;
        info.eventTypes = AccessibilityEvent.TYPES_ALL_MASK;
        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC;
        setServiceInfo(info);
    }

    @Override
    public void onInterrupt() {
    }
}
package com.mvp.cn.service;

import android.accessibilityservice.AccessibilityService;
import android.content.Intent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.mvp.cn.utils.LogUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by iqiao on 2020-02-18 09:03
 * Desc:
 */
public class InstallService extends AccessibilityService {

    private Map<Integer, Boolean> handleMap = new HashMap<>();

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        LogUtil.e("InstallService", "onAccessibilityEvent()");
        AccessibilityNodeInfo nodeInfo = event.getSource();
        if (nodeInfo != null) {
            int eventType = event.getEventType();
            if (eventType == AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED || eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
                if (handleMap.get(event.getWindowId()) == null) {
                    boolean handled = iterateNodesAndHandle(nodeInfo);
                    if (handled) {
                        handleMap.put(event.getWindowId(), true);
                    }
                }
            }
        }

    }

    // 遍历节点，模拟点击安装按钮
    private boolean iterateNodesAndHandle(AccessibilityNodeInfo nodeInfo) {
        LogUtil.e("InstallService", "iterateNodesAndHandle()");
        if (nodeInfo != null) {
            int childCount = nodeInfo.getChildCount();
            if ("android.widget.Button".equals(nodeInfo.getClassName())) {
                String nodeCotent = nodeInfo.getText().toString();
                LogUtil.e("InstallService", "content is: " + nodeCotent);
                //继续安装是华为的手机
                if ("继续安装".equals(nodeCotent) || "完成".equals(nodeCotent) || "确定".equals(nodeCotent) || "安装".equals(nodeCotent)) {
                    nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                    return true;
                }
            }
            // 遇到 ScrollView 的时候模拟滑动一下
            else if ("android.widget.ScrollView".equals(nodeInfo.getClassName())) {
                nodeInfo.performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD);
            }
            for (int i = 0; i < childCount; i++) {
                AccessibilityNodeInfo childNodeInfo = nodeInfo.getChild(i);
                if (iterateNodesAndHandle(childNodeInfo)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void onInterrupt() {

    }


    @Override
    public void onCreate() {
        LogUtil.e("InstallService", "onCreate()");
        super.onCreate();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        LogUtil.e("InstallService", "onStart()");
        super.onStart(intent, startId);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtil.e("InstallService", "onStartCommand()");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        LogUtil.e("InstallService", "onDestroy()");
        super.onDestroy();
    }
}

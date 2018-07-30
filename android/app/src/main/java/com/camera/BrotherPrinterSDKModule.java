package com.camera;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.brother.ptouch.sdk.LabelInfo;
import com.brother.ptouch.sdk.Printer;
import com.brother.ptouch.sdk.PrinterInfo;
import com.brother.ptouch.sdk.PrinterStatus;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import static com.facebook.react.bridge.UiThreadUtil.runOnUiThread;

public class BrotherPrinterSDKModule extends ReactContextBaseJavaModule {
    public static final String REACT_CLASS = "BrotherPrinterSDK";
    private static ReactApplicationContext reactContext = null;


    public BrotherPrinterSDKModule(ReactApplicationContext context) {
        // Pass in the context to the constructor and save it so you can emit events
        // https://facebook.github.io/react-native/docs/native-modules-android.html#the-toast-module
        super(context);
        reactContext = context;
    }

    @Override
    public String getName() {
        // Tell React the name of the module
        // https://facebook.github.io/react-native/docs/native-modules-android.html#the-toast-module
        return REACT_CLASS;
    }

    @ReactMethod
    public void print(@NonNull String path, @NonNull String ipAddress) {




        Log.w("Print Path", "" + path);
        Thread thread = new Thread(() -> {
            Bitmap bitmap = BitmapFactory.decodeFile(Uri.parse(path).getPath());


            Printer printer = new Printer();
            PrinterInfo printInfo = new PrinterInfo();
            printInfo.printerModel = PrinterInfo.Model.QL_720NW;
            printInfo.port = PrinterInfo.Port.NET;

//            printInfo.paperSize = PrinterInfo.PaperSize.CUSTOM;
//            printInfo.orientation = PrinterInfo.Orientation.LANDSCAPE;
//            printInfo.valign = PrinterInfo.VAlign.MIDDLE;
//            printInfo.align = PrinterInfo.Align.CENTER;
//            printInfo.printMode = PrinterInfo.PrintMode.ORIGINAL;
//            printInfo.numberOfCopies = 1;

            printInfo.printQuality = PrinterInfo.PrintQuality.NORMAL;
            printInfo.labelNameIndex = LabelInfo.QL700.W62.ordinal();
            printInfo.ipAddress = ipAddress.trim();
//            printInfo.isAutoCut = true;
//            printInfo.isCutAtEnd = false;
//            printInfo.isHalfCut = false;
//            printInfo.isSpecialTape = false;
//
//            if (TextUtils.isEmpty(ipAddress)) {
//                runOnUiThread(() ->
//                        Toast.makeText(reactContext,
//                                "Printer not found", Toast.LENGTH_LONG).show());
//                return;
//            }
//
//            PrinterInfo printInfo = getPrinterInfo();
//            printInfo.ipAddress = ipAddress.trim();
//
//            Printer printer = new Printer();
        printer.setPrinterInfo(printInfo);
//
//            // start printing
            printer.startCommunication();
            PrinterStatus printerStatus = printer.printImage(bitmap);
            if (printerStatus.errorCode != PrinterInfo.ErrorCode.ERROR_NONE) {
                runOnUiThread(() -> Toast.makeText(reactContext,
                        printerStatus.errorCode.toString(), Toast.LENGTH_LONG).show());
            }
            printer.endCommunication();

            // promise.resolve("Resolve");
            // emitDeviceEvent(EVENT_PRINT_SUCCESS);
        });
        thread.start();
    }


//    private void print(@NonNull Bitmap bitmap, @NonNull String ipAddress) {
        // https://facebook.github.io/react-native/docs/native-modules-android.html#the-toast-module
//        if (TextUtils.isEmpty(ipAddress)) {
//            runOnUiThread(() ->
//                    Toast.makeText(reactContext,
//                            "Printer not found", Toast.LENGTH_LONG).show());
//            return;
//        }

//        PrinterInfo printInfo = getPrinterInfo();
//        printInfo.ipAddress = ipAddress.trim();
//
//        Printer printer = new Printer();
////        printer.setPrinterInfo(printInfo);
//
//        // start printing
//        printer.startCommunication();
//        PrinterStatus printerStatus = printer.printImage(bitmap);
//        if (printerStatus.errorCode != PrinterInfo.ErrorCode.ERROR_NONE) {
//            runOnUiThread(() -> Toast.makeText(reactContext,
//                    printerStatus.errorCode.toString(), Toast.LENGTH_LONG).show());
//        }
//        printer.endCommunication();
//    }

    @NonNull
    private PrinterInfo getPrinterInfo() {
        // TODO allow changing these from JS
        PrinterInfo printInfo = new PrinterInfo();
        printInfo.printerModel = PrinterInfo.Model.QL_720NW;
        printInfo.port = PrinterInfo.Port.NET;
        printInfo.printQuality = PrinterInfo.PrintQuality.NORMAL;
        printInfo.labelNameIndex = LabelInfo.QL700.W62.ordinal();
        return printInfo;
    }

    private static void emitDeviceEvent(String eventName, @Nullable WritableMap eventData) {
        // A method for emitting from the native side to JS
        // https://facebook.github.io/react-native/docs/native-modules-android.html#sending-events-to-javascript
        reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(eventName, eventData);
    }
}

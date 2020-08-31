package com.example.apjakdojade

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.Result
import me.dm7.barcodescanner.zxing.ZXingScannerView

class QrCode : AppCompatActivity(), ZXingScannerView.ResultHandler {
    var scannerView: ZXingScannerView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        scannerView = ZXingScannerView(this)
        setContentView(scannerView)
    }

    override fun handleResult(result: Result) {
        result1 = result.text
        //MainActivity.resultTextView.setText(result.getText());
        val intent = Intent(this, WebActivity::class.java)
        startActivity(intent)
        //Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(result.getText()));
        //startActivity(intent);
        onBackPressed()
    }

    override fun onPause() {
        super.onPause()
        scannerView!!.stopCamera()
    }

    override fun onResume() {
        super.onResume()
        scannerView!!.setResultHandler(this)
        scannerView!!.startCamera()
    }

    companion object {
        @JvmField
        var result1: String? = null
    }
}
package ads.admob

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    lateinit var webView: WebView
    var adMob : AdMob? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var textView = findViewById<TextView>(R.id.textView)

        textView.movementMethod = ScrollingMovementMethod()

        webView =  findViewById<WebView>(R.id.webView)

        webView.webViewClient = WebViewClient()

        // this will load the url of the website
        webView.loadUrl("https://www.example.com/")

        // this will enable the javascript settings
        webView.settings.javaScriptEnabled = true

        // if you want to enable zoom feature
        webView.settings.setSupportZoom(true)

        adMob = AdMob(this as Context, this as Activity)

        adMob?.initialize()

    }

    fun one(view: View){
        adMob?.load_InterstitialAd()
        adMob?.show_InterstitialAd()
    }

    fun two(view: View){
        adMob?.load_RewardAd()
        adMob?.show_RewardAd(webView, "")
    }

    fun three(view: View){
        adMob?.show_BannerAd()
    }

}
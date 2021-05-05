package ads.admob

import android.app.Activity
import android.content.Context
import android.webkit.WebView
import android.widget.FrameLayout
import android.widget.TextView
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback

class AdMob (context : Context, activity: Activity) {

    private var mActivity: Activity
    private var mContext: Context

    private var mInterstitialAd: InterstitialAd? = null
    private var mRewardedAd: RewardedAd? = null
    private var rewardFlag:Boolean = false

    init {
        mContext = context
        mActivity = activity
    }

    fun initialize(){
        MobileAds.initialize(mContext) {
            load_InterstitialAd()
            load_RewardAd()
        }
    }


    fun show_BannerAd(){
        val frameLayout: FrameLayout = mActivity.findViewById(R.id.adView)
        var adId = "ca-app-pub-3940256099942544/6300978111"
        var adView = AdView(mContext)
        adView.setAdSize(AdSize.BANNER)
        adView.setAdUnitId(adId)
        val adRequest: AdRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
        frameLayout.addView(adView)
        toast( "AD Banner Showed Successfully...")
    }

    fun load_InterstitialAd(){
        var adRequest = AdRequest.Builder().build()

        InterstitialAd.load(mContext, "ca-app-pub-3940256099942544/1033173712", adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                toast(adError?.message)
                mInterstitialAd = null
                toast( "Interstitial ad failed to load...")
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                toast("Interstitial Ad loaded sccuessfully...")
                mInterstitialAd = interstitialAd

            }
        })

    }

    fun show_InterstitialAd() {
        if (mInterstitialAd != null) {
            toast( "Interstitial Ad showed fullscreen content.")
            mInterstitialAd?.show(mActivity)
        } else {
            toast( "The interstitial ad wasn't ready yet..")
        }
    }

    fun load_RewardAd(){

        var adRequest = AdRequest.Builder().build()

        RewardedAd.load(mContext,"ca-app-pub-3940256099942544/5224354917", adRequest, object : RewardedAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                mRewardedAd = null
                toast( "Reward ad failed to load..."+adError.toString())

            }

            override fun onAdLoaded(rewardedAd: RewardedAd) {
                toast("Reard Ad  was loaded.")
                mRewardedAd = rewardedAd
            }
        })

        mRewardedAd?.fullScreenContentCallback = object: FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                if(!rewardFlag){
                    toast( "Sorry! not rewared...")
                }
            }

            override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
                toast("Reward Ad failed to show."+adError.toString())
            }

            override fun onAdShowedFullScreenContent() {
                toast("Ad showed fullscreen content.")
                // Don't set the ad reference to null to avoid showing the ad a second time.
                //  mRewardedAd = null
            }
        }
    }

    fun show_RewardAd(webView: WebView, url: String){
        if (mRewardedAd != null){
            mRewardedAd?.show(mActivity){
                rewardFlag = true
                webView.loadUrl("https://httpbin.org/ip")
            }
        } else {
            toast( "The rewarded ad wasn't ready yet.")
        }
    }



    fun toast(msg : String){
        mActivity.findViewById<TextView>(R.id.textView).append("\n\n"+msg)
    }

}
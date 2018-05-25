# FermaxSDK document for integrators

## Using the SDK


In order to use the FermaxSDK module, it will be necessary to extend the application class FermaxApp and initialize the SDK and establish the launcher activity and declare the class in AndroidManifest.xml.


Extending FermaxApp example
```
public class LynxedApplication extends FermaxApp {
    @Override
    public void onCreate() {
        super.onCreate();

        Fabric.with(this, new Crashlytics());
        FermaxSDK.initialize(this);
        FermaxSDK.setLauncherActivityClass(LynxedLauncherActivity.class);
    }
}
```

Declare your custom application class in AndroidManifest.xml
```
<application
    android:name=".application.LynxedApplication" <!-- Here it is declared -->
    android:allowBackup="true"
    android:icon="@mipmap/ic_launcher"
    android:label="@string/app_name"
    android:supportsRtl="true"
    android:theme="@style/AppTheme">
```

Once we have configured the app to use the SDK, we can do customizations in layout resources. For example if we would like to change the image that is displayed at the splash screen we could do something like this on the **onCreate()** method of the subclass.

```
public class LauncherActivityImpl extends LauncherActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_launcher);

        final ImageView splashImageView = (ImageView) findViewById(R.id.splash);
        splashImageView.setImageResource(R.drawable.splash_icon);
    }
}
```


## Layout resource tree of the main classes

* **MainActivity**

    * Main activity. It manages the keepAlive thread and the states of the network. In this activity are managed:

        * Default preferences are loaded.
        * Check the status of the VPN and the call.
        * License error messages are displayed (not valid, expired or not found) if they are given.
        * Check the certificates and the connection to the Strongswan service.

    * Layout resources:

        activity_main.xml
        >       LinearLayout
        >           RelativeLayout
        >               ImageView @+id/imageView1   - Image of Fermax logo
        >           FrameLayout @+id/container      - References to fragment_main.xml or fragment_main_linked.xml

        activity_main_linked.xml
        >       LinearLayout
        >         RelativeLayout
        >             ImageButton @+id/linkedStateBtnDis    - Image of VPN state disconnected
        >             ImageButton @+id/linkedStateBtnWarn   - Image of VPN state warning
        >             ImageButton @+id/linkedStateBtnCon    - Image of VPN state connected
        >             ImageButton @+id/settingsBtn          - Image of settings
        >             ImageView @+id/imageView1             - Image of Fermax logo
        >         FrameLayout @+id/container                - References to fragment_main.xml or fragment_main_linked.xml

        fragment_main_linked.xml
        >       RelativeLayout
        >             LinearLayout
        >                Button @+id/bUnLink    - Button to execute pair off action
        >                Button @+id/bOpenDoor  - Button to execute open door action
        >             RelativeLayout
        >                 TextView @+id/tvInfo  - TextView of footer info


* **LicenseActivity**

    * In this activity, the license number is validated and the user's information is provided: name, surname, phone number ...

    * Layout resources:

        activity_license.xml:
        >       RelativeLayout
        >           include @+id/toolbar                                    - References to toolbar_center_title.xml
        >           RelativeLayout
        >               Button @+id/validate_action_bt                      - Button to execute validate action
        >       ScrollView
        >           RelativeLayout
        >               LinearLayout @+id/login_layout                      - The login layout
        >                   LinearLayout
        >                       TextView                                    - References to @string/login_license_number_hint
        >                        LinearLayout
        >                        EditText @+id/login_user_license_et        - EditText of user license number
        >                        ImageView @+id/scanqr                      - Button to execute QR Scan action
        >                   LinearLayout
        >                        TextView                                   - References to @string/login_phone_hint
        >                        EditText @+id/login_user_phone_number_et   - EditText of user phone number
        >                   LinearLayout
        >                        TextView                                   - References to @string/login_email_hint
        >                        EditText @+id/login_user_email_et          - EditText of user email
        >                   LinearLayout
        >                        TextView                                   - References to @string/login_first_name_hint
        >                        EditText @+id/login_user_name_et           - EditText of user name
        >                   LinearLayout
        >                        TextView                                   - References to @string/login_last_name_hint
        >                        EditText @+id/login_user_last_name_et      - EditText of user last name
        >               RelativeLayout @+id/login_progress_layout           - Progress layout
        >                    ProgressBar @+id/progressbar                   - Progress Bar


* **ConfigurationActivity**

    * In this activity we can configure different parameters such as device name, busy mode, microphone gain and video quality, and the action of "Push to talk".

    * Layout resources:

        activity_configuration.xml
        >       RelativeLayout
        >           include @+id/toolbar
        >           ScrollView
        >               LinearLayout @+id/configurationLayout                   - The configuration main layout
        >                   LinearLayout
        >                       RelativeLayout @+id/pushtotalk_layout           - The push to talk action layout
        >                           ImageView @+id/push_to_talk_iv              - Image of push to talk action
        >                           TextView @+id/push_to_talk_tw               - TextView of push to talk action
        >                       RelativeLayout
        >                           SwitchCompat @+id/busy_switch               - Switch of busy mode
        >                           TextView @string/configuration_busy_mode    - Text of busy mode
        >                   RelativeLayout
        >                       TextView @+id/device_name_title_tw          - TextView of device name
        >                       EditText @+id/device_name_tw                - EditText of device name
        >                   TextView                                        - References to @string/configuration_microphone_gain
        >                   RelativeLayout
        >                       TextView                                    - References to @string/configuration_handsfree
        >                       RelativeLayout
        >                           ImageView @+id/micro_low_hf_iv          - Image of low speaker
        >                           SeekBar @+id/micro_handsfree_sb         - SeekBar for handsfree microphone gain
        >                           ImageView @+id/micro_high_hf_iv         - Image of high speaker
        >                   RelativeLayout
        >                       TextView @string/configuration_headset
        >                       RelativeLayout
        >                           ImageView @+id/micro_low_hs_iv          - Image of low speaker
        >                           SeekBar @+id/micro_headset_sb           - SeekBar for headset microphone gain
        >                           ImageView @+id/micro_high_hs_iv         - Image of high speaker
        >                   TextView @string/configuration_video_quality
        >                   RelativeLayout
        >                       TextView                                    - References to @string/configuration_wifi
        >                       TextView @+id/value_hf_tw                   - TextView of wifi activated state
        >                       ImageView @+id/video_low_hf_iv              - Image of low video quality
        >                       ImageView @+id/video_high_hf_iv             - Image of high video quality
        >                       LinearLayout
        >                           View
        >                           View
        >                           View
        >                       SeekBar @+id/video_handsfree_sb             - SeekBar for video quality handsfree
        >                   RelativeLayout
        >                       TextView                                    - References to @string/configuration_3G
        >                       TextView  @+id/value_hs_tw                  - TextView of 3G state activated
        >                       ImageView @+id/video_low_hs_iv              - Image of low video quality
        >                       ImageView @+id/video_high_hs_iv             - Image of high video quality
        >                       LinearLayout
        >                           View
        >                           View
        >                           View
        >                       SeekBar @+id/video_headset_sb               - SeekBar for video quality headSet
        >                   TextView                                        - References to @string/activity_license
        >                   LinearLayout @+id/validate_iv_layout            - Validate license Layout
        >                       ImageView @+id/validate_iv                  - Image of checkbox to validate license
        >                       LinearLayout
        >                           TextView @+id/validate_tw               - TextView of validate license
        >                           TextView @+id/validate_until_tw         - TextView of validate license until
        >                       RelativeLayout
        >                           ImageView
        >                   RelativeLayout
        >                       TextView                                    - References @string/adv_ec
        >                       CheckBox @+id/ecocancelcb                   - CheckBox to cancel echo
        >                   RelativeLayout
        >                       TextView @+id/ecodelay_tw                   - Textview of eco delay
        >                       RelativeLayout
        >                           ImageView @+id/ecodelay_left_iv         - Image of eco delay left
        >                           SeekBar @+id/ecodelay_sb                - SeekBar of eco delay
        >                           ImageView @+id/ecodelay_right_iv        - Image of eco delay right
        >                   RelativeLayout
        >                       TextView @+id/ecotail_tw                    - TextView of eco tail
        >                       RelativeLayout
        >                           ImageView @+id/ecotail_left_iv          - Image of eco tail left
        >                           SeekBar @+id/ecotail_sb                 - SeekBar of eco tail
        >                           ImageView @+id/ecotail_right_iv         - Image of eco tail right
        >                   RelativeLayout
        >                       TextView                                    - References to @string/adv_ng
        >                       CheckBox @+id/noisegate_cb                  - CheckBox to noise control
        >                   RelativeLayout
        >                       TextView @+id/noisethreshold_tw             - TextView of noise thereshold
        >                       RelativeLayout
        >                           ImageView @+id/noise_hight_hs_iv        - Image of noise high
        >                           SeekBar @+id/noise_sb                   - SeekBar of noise control
        >                           ImageView @+id/noise_low_hs_iv          - Image of noise low
        >                   TextView @+id/versionTw                         - TextView of version




* **LicenseInfoActivity**

     * In this activity it could be seen the information related to the license.

     * Layout resources:

        activity_license_info.xml

         >      RelativeLayout
         >          include @+id/toolbar                    - References to @layout/toolbar_center_title
         >          RelativeLayout @+id/bottomLayout
         >              Button @+id/license_info_action_bt  - Button for execute accept action
         >          LinearLayout
         >              RelativeLayout
         >                  TextView @+id/license_info_tw   - TextView of license insertion info
         >                  ImageButton
         >                  RelativeLayout
         >                      LinearLayout
         >                          ImageView
         >                          ImageButton


* **CallActivity**

    * In this activity the part of the calls is managed.

    * Layout resources:

       activity_main_linked

        >       LinearLayout
        >           RelativeLayout
        >               ImageButton @+id/linkedStateBtnDis      - Image of VPN state disconnected
        >               ImageButton @+id/linkedStateBtnWarn     - Image of VPN state warning
        >               ImageButton @+id/linkedStateBtnCon      - Image of VPN state connected
        >               ImageButton @+id/settingsBtn            - Image of settings
        >               ImageView @+id/imageView1               - Image of Fermax logo
        >           FrameLayout @+id/container                  - References to fragment_main.xml or fragment_main_linked.xml


* **LauncherActivityImpl**

    * In this activity you can configure the image that appears on the splash or startup screen.

    * Layout resources:

       laucher.xml

        >       RelativeLayout
        >           RelativeLayout @+id/splash_progress_layout  - Progress bar layout
        >               ProgressBar
        >           ImageView @+id/splash                       - Image of splash


* **ClufActivity**

    * In this activity we can accept or cancel the terms and conditions. These terms and conditions are loaded from a url.

    * Layout resources:

       activity_cluf.xml

        >       RelativeLayout
        >           include @+id/toolbar            - References to @layout/toolbar_center_title
        >           LinearLayout
        >               Button @+id/cancelButton    - Button to execute cancel action
        >               Button @+id/agreeButton     - Button to execute agree action
        >           ClufWebView @+id/webView        - WebView of license agreement


* AnsweredCallFragment

    * Fragment of "Call in progress". Shows the video / audio. Here it could be managed:

        * Handsfree activation or deactivation.
        * The action of the pushToTalk: Mute / Activate micro and redirect the audio to the speech to the headset.

    * Layout resources:

        fragment_answered.xml

        >       RelativeLayout
        >           ImageView @+id/btnSpeaker                   - Image to execute speaker toggle action
        >           TextView @+id/timeCall 00:00                - TextView of time call
        >           ImageView @+id/btnVideo                     - Image to execute video toggle action
        >           LinearLayout @+id/stats                     - Stats layout
        >               LinearLayout
        >                   TextView                            - References to Audio Bit Width:
        >                   TextView @+id/audioBW               - TextView of Audio Bit Width:
        >               LinearLayout
        >                   TextView                            - References to Video Bit Width:
        >                   TextView @+id/videoBW               - TextView of Video Bit Width
        >               LinearLayout
        >                   TextView                            - References to Sender LossRate:
        >                   TextView @+id/senderLR              - TextView of Sender Loss Rate
        >               LinearLayout
        >                   TextView                            - References to Receiver Loss Rate:
        >                   TextView @+id/receiverLR            - TextView of Receiver Loss Rate
        >               LinearLayout
        >               TextView                                - References to Call Quality:
        >                   TextView @+id/callQuality           - TextView of call quality
        >           ImageView @+id/PushToTalk                   - Image of push to talk action
        >           Chronometer @+id/chronometer                - Chronometer
        >       LinearLayout @+id/bottom_layout
        >           RelativeLayout
        >               ImageView @+id/hangUpAnswered           - Image to execute hang up action
        >           RelativeLayout @+id/pushToTalkLayout        - Layout of push to talk action
        >               ImageView @+id/pushToTalk               - Image to execute push to talk action
        >           RelativeLayout
        >               ImageView @+id/openDoor                 - ImageView view of open door action
        >       RelativeLayout
        >           TextView @+id/tvInfo @string/call_answered  - TextView of answer info
        >       FrameLayout @+id/FragmentContainer              - References to video.xml
        >           RelativeLayout @+id/center_layout           - Center layout
        >               AspectRatioImageView @+id/callerImage   - Image of Caller
        >               ImageView @+id/call                     - Image default for Caller


* **IncomingCallFragment**

    * Incoming call fragment. Here it is allowed to answer or hang up the call.

    * Layout resources:

       fragment_incoming.xml

        >       RelativeLayout
        >           RelativeLayout
        >               ImageView @+id/btnSpeaker                   - Image to execute toggle speaker action
        >               ImageView @+id/btnVideo                     - Image to execute video toggle action
        >           RelativeLayout @+id/center_layout
        >               AspectRatioImageView @+id/callerImage       - Image of caller
        >               ImageView @+id/call                         - Image default for Caller
        >           LinearLayout
        >               RelativeLayout
        >                   ImageView @+id/hangUpIncoming           - Image to execute hang up action
        >               RelativeLayout
        >                   ImageView @+id/answer                   - Image to execute answer action
        >           RelativeLayout
        >               TextView @+id/tvInfo @string/call_incoming  - TextView of call incoming info


## Authors

* **Develapps** - *Initial work* - [Develapps](https://www.develapps.com)

## License

This program is free software; you can redistribute it and/or modify it
under the terms of the GNU General Public License as published by the
Free Software Foundation; either version 2 of the License, or (at your
option) any later version.  See <http://www.fsf.org/copyleft/gpl.txt>.
This program is distributed in the hope that it will be useful, but
WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
for more details. See the [LICENSE.md](LICENSE.md) file for details



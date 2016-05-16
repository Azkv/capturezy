# Capturezy

<b>What is Capturezy?</b>
Building any app that needed a camera image capturing I've had headaches over and over. Wrong URIs, wrong paths, wrong photo orientation, too large files to decode to bitmaps, etc.. the list goes on.
I've decided to pull all the neccessary utilities into this small but useful library.

<b>Installation</b>
<code>
compile 'com.azkv:capturezy:0.2.1@aar'
</code>

<b>Usage</b>
in your manifest add the activity
<code>

        <activity android:name="com.azarkovic.capturezy.CameraPhotoCapture"></activity>
</code>
Starting image capture example
<code>

        String imageFileName = "myfilename";
        File storageDir = Environment.getExternalStorageDirectory();
        File realStorage = new File(storageDir+"/mydir");
        Intent intent = new Intent(this,CameraPhotoCapture.class);
        intent.putExtra("path",realStorage.getAbsolutePath());
        intent.putExtra("filename",imageFileName);
        intent.putExtra("extension","jpg");
        startActivityForResult(intent,REQUEST_CAMERA);
</code>
Starting with config
<code>

        String imageFileName = "myfilename";
        File storageDir = Environment.getExternalStorageDirectory();
        File realStorage = new File(storageDir+"/mydir");
        //scale the resulting photo by 0.5 x 0.5
        Config config = new Config(0.5f,0.5f);
        //generate a thumbnail for our photo of size 100,100
        config.generateThumbnail(100,100);
        Intent intent = new Intent(this,CameraPhotoCapture.class);
        intent.putExtra("path",realStorage.getAbsolutePath());
        intent.putExtra("filename",imageFileName);
        intent.putExtra("extension","jpg");
        intent.putExtra("config", config);
        startActivityForResult(intent,REQUEST_CAMERA);
</code>
And simply catch the bitmaps in the onActivityResult() like this
<code>

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) return;

        if(requestCode == REQUEST_CAMERA)
        {
            Result rez = (Result)data.getSerializableExtra("result");
            if(rez.getResultImagePath() != null)
            {
                Bitmap bmp = BitmapFactory.decodeFile(rez.getResultImagePath());
                Bitmap thumbnail = BitmapFactory.decodeFile(rez.getResultThumbnailPath());
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
</code>





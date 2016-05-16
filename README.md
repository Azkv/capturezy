# Capturezy

<b>What is Capturezy?</b>
Building any app that needed a camera image capturing I've had headaches over and over. Wrong URIs, wrong paths, wrong photo orientation, too large files to decode to bitmaps, etc.. the list goes on.
I've decided to pull all the neccessary utilities into this small but useful library.

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



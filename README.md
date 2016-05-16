# Capturezy

##What is Capturezy?
Building any app that needed a camera image capturing I've had headaches over and over. Wrong URIs, wrong paths, wrong photo orientation, too large files to decode to bitmaps, etc.. the list goes on.
I've decided to pull all the neccessary utilities into this small but useful library.

###Installation
```java
compile 'com.azkv:capturezy:0.2.1@aar'
```

####Usage
in your manifest add the activity
```java
<activity android:name="com.azarkovic.capturezy.CameraPhotoCapture"></activity>
```

Starting image capture example
```java
String imageFileName = "myfilename";
File storageDir = Environment.getExternalStorageDirectory();
File realStorage = new File(storageDir+"/mydir");
Intent intent = new Intent(this,CameraPhotoCapture.class);
intent.putExtra("path",realStorage.getAbsolutePath());
intent.putExtra("filename",imageFileName);
intent.putExtra("extension","jpg");
startActivityForResult(intent,REQUEST_CAMERA);
```
or start with config
```java
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
```
Config options:
```java
public Config()                                             //EMPTY - used mostly just to generate thumbnails
public Config(int width, int height)                        //SPECIFIC OUTPUT SIZE
public Config(float scaleX, float scaleY)                   //SCALED
public Config(int x, int y, int w, int h)                   //CROPPED
public void generateThumbnail(int width, int height)        //THUMBNAIL SPECIFIC SIZE
public void generateThumbnail(float scaleX, float scaleY)   //THUMBNAIL SCALED
```

And simply catch the bitmaps in the onActivityResult() like this
```java
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
```





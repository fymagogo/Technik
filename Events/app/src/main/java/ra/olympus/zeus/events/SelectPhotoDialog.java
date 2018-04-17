package ra.olympus.zeus.events;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by fawkes on 4/6/2018.
 */

public class SelectPhotoDialog extends DialogFragment {

    private static final String TAG = "SelectPhotoDialog";
    private static final int PICKFILE_REQUEST_CODE = 1234;
    private static final int CAMERA_REQUEST_CODE = 4321;

    public interface OnPhotoSelectedListener{
        void getImagePath(Uri imagePath);
        void getImageBitmsp(Bitmap bitmap);

    }
    OnPhotoSelectedListener mOnPhotoSelectedListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_select_photo,container,false);
        TextView GalleryImage = (TextView) view.findViewById(R.id.GalleryImage);
        GalleryImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"accessing phone's gallery");
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, PICKFILE_REQUEST_CODE );
            }
        });

        TextView OpenCamera = (TextView) view.findViewById(R.id.OpenCamera);
        OpenCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"starting camera");
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAMERA_REQUEST_CODE );
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Results when selecting new image from gallery
        if (requestCode == PICKFILE_REQUEST_CODE ){
            if (resultCode == Activity.RESULT_OK) {
                Uri selectedImageUri = data.getData();
                Log.d(TAG,"onActivityResult: " + selectedImageUri);
                //send Uri to CreateEvent Activity
                mOnPhotoSelectedListener.getImagePath(selectedImageUri);
                getDialog().dismiss();

            }




            //Results when selecting new image from gallery

        }else if (requestCode == CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            Log.d(TAG,"onActivityResult: DOne taking new photo");
            Bitmap bitmap;
            bitmap = (Bitmap) data.getExtras().get("data");

            //send photo to CreateEvent Activity
            mOnPhotoSelectedListener.getImageBitmsp(bitmap);
            getDialog().dismiss();


        }
    }

    @Override
    public void onAttach(Context context) {
        try {
            mOnPhotoSelectedListener = (OnPhotoSelectedListener) getActivity();
        }catch (ClassCastException e){
            Log.e(TAG,"onAttach : ClassCastException" + e.getMessage());
        }
        super.onAttach(context);
    }
}

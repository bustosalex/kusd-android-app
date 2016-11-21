package edu.uwp.kusd.boardMembers;

import android.graphics.Bitmap;

import java.util.Date;

/**
 * Created by Dakota on 10/21/2016.
 */

public class BoardMember {

    private Bitmap mPhoto;
    private String mPhotoURL;
    private String mName;
    private String mPosition;
    private String mEmail;
    private String mPhone;
    private Date mTerm;

    public BoardMember(Bitmap photo, String photoURL, String name, String position, String email, String phone, Date term) {
        mPhoto = photo;
        mPhotoURL = photoURL;
        mName = name;
        mPosition = position;
        mEmail = email;
        mPhone = phone;
        mTerm = term;
    }

    public String getPhotoURL() {
        return mPhotoURL;
    }

    public String getName() {
        return mName;
    }

    public String getPosition() {
        return mPosition;
    }

    public String getEmail() {
        return mEmail;
    }

    public String getPhone() {
        return mPhone;
    }

    public Date getTerm() {
        return mTerm;
    }
}

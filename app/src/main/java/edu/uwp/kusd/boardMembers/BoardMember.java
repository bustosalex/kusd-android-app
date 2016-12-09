package edu.uwp.kusd.boardMembers;

import android.graphics.Bitmap;

import java.util.Date;

/**
 * Created by Dakota on 10/21/2016.
 */

public class BoardMember {

    /**
     * The photo url for a board member
     */
    private String mPhotoURL;

    /**
     * The name of a board member
     */
    private String mName;

    /**
     * The position for a board member
     */
    private String mPosition;

    /**
     * The email for a board member
     */
    private String mEmail;

    /**
     * The phone number for a board member
     */
    private String mPhone;

    /**
     * The term for a board member
     */
    private Date mTerm;

    /**
     * Constructs a board member
     *
     * @param photoURL the photo url for a board member
     * @param name the name of a board member
     * @param position the position the board member holds
     * @param email the email of the board member
     * @param phone the phone number for a board member
     * @param term the term of a board member
     */
    public BoardMember(String photoURL, String name, String position, String email, String phone, Date term) {
        mPhotoURL = photoURL;
        mName = name;
        mPosition = position;
        mEmail = email;
        mPhone = phone;
        mTerm = term;
    }

    /**
     * Gets the photo url for a board member
     *
     * @return the photo url for a board member
     */
    public String getPhotoURL() {
        return mPhotoURL;
    }

    /**
     * Gets the name of a board member
     *
     * @return the name of a board member
     */
    public String getName() {
        return mName;
    }

    /**
     * Gets the position of a board member
     *
     * @return the position of a board member
     */
    public String getPosition() {
        return mPosition;
    }

    /**
     * Gets the email of a board member
     *
     * @return the email of a board member
     */
    public String getEmail() {
        return mEmail;
    }

    /**
     * Gets the phone number of a board member
     *
     * @return the phone number of a board member
     */
    public String getPhone() {
        return mPhone;
    }

    /**
     * Gets the term of a board member
     *
     * @return the term of a board member
     */
    public Date getTerm() {
        return mTerm;
    }
}

package thaihn.android.daggerandroid.data.model;

public class User {

    private Long mId;
    private String mName;
    private String mAddress;
    private String mCreatedAt;
    private String mUpdatedAt;

    public User() {
    }

    public User(String name, String address) {
        mName = name;
        mAddress = address;
    }

    public User(Long id, String name, String address, String createdAt, String updatedAt) {
        mId = id;
        mName = name;
        mAddress = address;
        mCreatedAt = createdAt;
        mUpdatedAt = updatedAt;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        mAddress = address;
    }

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        mCreatedAt = createdAt;
    }

    public String getUpdatedAt() {
        return mUpdatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        mUpdatedAt = updatedAt;
    }
}

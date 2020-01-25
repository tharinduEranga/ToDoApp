package lk.ijse.gdse.tharindu.todo.db;

import android.provider.BaseColumns;

public final class LoginAuthHandleStatements {

    public static final String LOGIN_USERNAME = "super";
    public static final String LOGIN_PASSWORD = "123";

    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private LoginAuthHandleStatements() {}

    /* Inner class that defines the table contents */
    public static class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "authenticated";
        public static final String USERNAME = "username";
        public static final String PASSWORD = "password";
        public static final String STATUS = "status";
    }

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedEntry.TABLE_NAME + " (" +
                    FeedEntry._ID + " INTEGER PRIMARY KEY," +
                    FeedEntry.USERNAME + " TEXT," +
                    FeedEntry.STATUS + " BOOLEAN," +
                    FeedEntry.PASSWORD + " TEXT)";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;
}
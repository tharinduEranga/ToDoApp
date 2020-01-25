package lk.ijse.gdse.tharindu.todo.broadcast;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.util.Objects;

public class MyBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = MyBroadcastReceiver.class.toString();

    @Override
    public void onReceive(Context context, Intent intent) {
        final PendingResult pendingResult = goAsync();
        switch (Objects.requireNonNull(intent.getAction())) {
            case Intent.ACTION_AIRPLANE_MODE_CHANGED:
                Toast.makeText(context, "Airplane mode changed ", Toast.LENGTH_SHORT).show();
                break;
            case BluetoothAdapter.ACTION_STATE_CHANGED:
                BluetoothChangeAction bluetoothChangeAction = new BluetoothChangeAction(pendingResult, intent);
                bluetoothChangeAction.execute();
                Toast.makeText(context, "Bluetooth changed ", Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(context, "Nothing changed ", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private static class BluetoothChangeAction extends AsyncTask<String, Integer, String> {

        private final PendingResult pendingResult;
        private final Intent intent;

        private BluetoothChangeAction(PendingResult pendingResult, Intent intent) {
            this.pendingResult = pendingResult;
            this.intent = intent;
        }

        @Override
        protected String doInBackground(String... strings) {
            StringBuilder sb = new StringBuilder();
            sb.append("Action: " + intent.getAction() + "\n");
            sb.append("URI: " + intent.toUri(Intent.URI_INTENT_SCHEME).toString() + "\n");
            String log = sb.toString();
            Log.d(TAG, log);
            return log;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            // Must call finish() so the BroadcastReceiver can be recycled.
            pendingResult.finish();
        }
    }
}
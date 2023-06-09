package mx.mauriciogs.mibanca.location.permission

import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment

class PermissionRequester(fragment: Fragment, private val permission: String, onDenied: () -> Unit = {}, onShowRationale: () -> Unit = {}) {

    private var onGranted: () -> Unit = {}

    private val launcher = fragment.registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            when {
                isGranted -> onGranted()
                fragment.shouldShowRequestPermissionRationale(permission) -> onShowRationale()
                else -> onDenied()
            }
        }

    fun runWithPermission(onGranted: () -> Unit) {
        this.onGranted = onGranted
        launcher.launch(permission)
    }
}
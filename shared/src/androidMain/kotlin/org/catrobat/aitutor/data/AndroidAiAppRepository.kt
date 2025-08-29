package org.catrobat.aitutor.data

import android.content.pm.PackageManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.catrobat.aitutor.domain.model.AiAppInfo
import org.catrobat.aitutor.domain.model.AndroidPlatformImage
import org.catrobat.aitutor.domain.repository.AiAppRepository

class AndroidAiAppRepository(
    private val dataSource: InstalledAiAppDataSource,
    private val packageManager: PackageManager,
) : AiAppRepository {
    override suspend fun getInstalledAiApps(): List<AiAppInfo> =
        withContext(Dispatchers.IO) {
            dataSource.getInstalledAiAppsInfo().map { appInfo ->
                AiAppInfo(
                    appName = appInfo.loadLabel(packageManager).toString(),
                    packageName = appInfo.packageName,
                    icon = AndroidPlatformImage(androidDrawable = appInfo.loadIcon(packageManager)),
                )
            }
        }
}

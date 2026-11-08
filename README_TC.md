# Wear Memory 0.1.1 Unslop UI

Wear OS 錄音 App ＋ Android 手機本機 AI 伴侶 App。這是第一個開發版本，未通過實錶 24 小時穩定性或實機模型速度測試，不能視作完整正式版。

## 安裝

1. 手機安裝 WearMemory_Phone_0.1.1_unslopUI.apk。
2. 手錶透過無線 ADB 安裝 WearMemory_Watch_0.1.1_unslopUI.apk。
3. 手機與手錶須以 Google Play services 配對，並安裝同一組簽章 APK。
4. 手錶授予咪高峰權限；手機授予通知權限。
5. 在手錶按「開始錄音」，返回錶面後繼續錄音；系統錄音提示保留。重新開機需要手動開始。
6. 手機「AI 模型管理」分別匯入 Whisper 多語言 ggml .bin 與 Qwen3 GGUF 模型。模型沒有包含於 APK；會佔用額外儲存空間。
7. 先錄短片段並停止錄音，讓手錶同步；手機按「立即本機分析」檢查轉錄及摘要，再設定夜間時間。

## 支援範圍

- 手錶 16 kHz 單聲道 PCM，每 5 分鐘完成一段；停止時儲存未滿 5 分鐘片段。
- Wear Data Layer 傳送 Asset，手機寫入完成後刪除同步項目以確認收件。
- 手機接收後手錶清理副本；未收到確認前保留。
- 手機播放、逐字稿、刪除對應錄音與分析結果。
- whisper.cpp CPU 本機轉錄及 llama.cpp CPU 本機摘要。沒有雲端 AI 備援。
- 每日及每月摘要包含待辦、日期草稿及關鍵詞，結果需核對來源。
- Qwen3 提示格式與非 thinking 模式；其他架構的 GGUF 並不保證相容。
- 模型格式檢查及保留上一個模型，手動還原。
- 夜間 JobScheduler 及自訂早晨通知（系統可能延遲）。
- 溫度／充電條件於片段之間檢查；完成逐字稿後保存檢查點。
- 模型無法載入或記憶體不足會顯示失敗狀態，不能保證任意模型都可運行。

## 目前限制（必讀）

- Google Drive OAuth 及自動備份尚未實作；可用「匯出備份」經 Android 檔案選擇器選擇 Google Drive（需 Drive 提供者可用）或其他位置手動保存 ZIP。
- AI 會嘗試提取明確日期與時間作日曆草稿，須用戶核對後儲存；無法解析時可手動開啟日曆。尚未有取消或改期去重引擎。
- 沒有雲端服務、模型自動下載、OTA 更新服務或正式 Play 發佈配置。
- 錄音是未壓縮 PCM，約 2.76 GB／24 小時（十進位）；不是原計劃的壓縮音訊方案。請確保空間足夠。
- 未有自動錄音保留期限、手機磁碟滿預警（手錶餘量低於 100 MB 停止錄音）、復原部分損壞檔案或完整跨裝置刪除帳本。
- 沒有背景保證、全天續航保證、來電／助理咪高峰衝突處理保證。
- 不含個別講者辨識、互動記錄問答、真正詞頻統計圖表；關鍵詞由摘要模型生成。
- 文字模型於同一輪分析重用，結束後釋放；首版速度及散熱有待改善；沒有 GPU／NPU 路徑。
- 一次轉錄一個 5 分鐘分段，需可用記憶體。不能保證一晚處理完整日音訊。
- Android 排程受省電、工作配額及強制停止影響；已完成分析才顯示完成。
- 手動停止／系統停止進行中的原生分析，不保證立即中斷，於模型呼叫返回後退出。
- Alpha 使用 Android debug 簽章；正式發佈需改用持續保存的私人 release 簽章。

## 建議模型

Whisper small multilingual ggml（可試 tiny/base 做較快測試，但需核對粵語準確度）。下載：
https://huggingface.co/ggerganov/whisper.cpp/tree/main

Qwen3 1.7B Q4 GGUF：
https://huggingface.co/Qwen/Qwen3-1.7B-GGUF

模型遵守各自授權；選擇手機 RAM 能負擔的大小。原始音訊／文字存於 Android App 私有目錄；系統備份已停用。錄音前應讓參與者了解並取得適當同意。

## 編譯

JDK 17+、Gradle 8.10.2、Android SDK 35、NDK 27.2.12479018、CMake 3.22.1。

在專案根目錄建立 local.properties，填寫 sdk.dir，再執行：

    gradle :phone:assembleDebug :watch:assembleDebug

vendor 目錄包含 whisper.cpp 及 llama.cpp 原始碼與授權；兩個推論引擎獨立 native library，避免 GGML 全域符號互相覆蓋。

## 後續開發

1. 實機錄音／傳輸／模型測速及耗電驗證。
2. 壓縮音訊、磁碟上限、保留期限、可恢復同步及刪除帳本。
3. Google Cloud OAuth 設定、Drive 備份及還原。
4. 結構化日曆提取、去重、取消／改期處理及日期驗證。
5. 模型下載目錄、完整相容性測試、效能測試及 GPU／NPU 加速。
6. 資料庫版本遷移、正式簽章、更新頻道與發佈。

## 原始引擎版本

llama.cpp: 372dc5ca157276beb6c67b2e92477e8d662e964a

whisper.cpp: f92e1e4d1056700c39d4e86c1140f0a088fe1ab6

## 0.1.1 介面更新

保留功能及順序，改用炭灰、鼠尾草綠及三層文字色。所有間距改用 dp，主要操作與導航分開，日期與摘要先行，模型分類改成用戶容易理解的名稱。手錶改用圓形視窗留白、可捲動內容及最少 48 dp 觸控區。狀態列及底部導航使用系統安全邊界。沒有裝飾波形、假統計、循環動畫或生成圖片。

Visual craft adapted from unslop by Damir Sokolovsky (@heydamir), CC BY 4.0. https://github.com/heydamir/unslop

文字與按鈕主要配色對比通過 4.5:1 檢查。APK 編譯與簽章已檢查；未經實機畫面、字體放大及 24 小時運行驗證。嘗試本地瀏覽器排版校樣，但環境不允許建立所需 socket，因此沒有實機或瀏覽器截圖交付。

# 🎬 CloudStream Extensions Arabic - نظيف ومنظم

<p align="center">
  <img src="https://img.shields.io/badge/Extensions-48+-green.svg" alt="Extensions Count">
  <img src="https://codeberg.org/dhomred/cloudstream-extensions-arabic/workflows/Build/badge.svg" alt="Build Status">
  <img src="https://img.shields.io/badge/Language-Kotlin-blue.svg" alt="Language">
  <img src="https://img.shields.io/badge/License-MIT-yellow.svg" alt="License">
</p>

<p align="center">
  <strong>إضافات عربية متقدمة لـ CloudStream - بشكل نظيف ومنظم</strong>
</p>

## 📋 فهرس المحتويات

- [🎬 CloudStream Extensions Arabic - نظيف ومنظم](#-cloudstream-extensions-arabic---نظيف-ومنظم)
  - [📋 فهرس المحتويات](#-فهرس-المحتويات)
  - [🌟 المميزات](#-المميزات)
  - [📦 المزودين المتاحين](#-المزودين-المتاحين)
  - [🔧 التثبيت](#-التثبيت)
  - [🏗️ البنية المجلدية](#️-البنية-المجلدية)
  - [🔍 استخدام المستخرجات](#-استخدام-المستخرجات)
  - [📊 الإحصائيات](#-الإحصائيات)
  - [🛠️ التطوير](#️-التطوير)
  - [📝 المساهمة](#-المساهمة)
  - [📞 التواصل](#-التواصل)
  - [⚖️ الترخيص](#️-الترخيص)

## 🌟 المميزات

- ✅ **48+ موقع عربي ودولي** مدعوم
- ✅ **مستخرجات فيديو متقدمة** (StreamTape, DoodStream, MixDrop, MegaUp, FileMoon)
- ✅ **توثيق شامل** باللغة العربية
- ✅ **بنية منظمة** وسهلة التوسعة
- ✅ **اختبارات جودة** مدمجة
- ✅ **تحديثات دورية** وصيانة مستمرة
- ✅ **دعم فني** متجاوب

## 📦 المزودين المتاحين

### 📺 مزودين الأفلام والمسلسلات
| المزود | الحالة | النوع |
|--------|--------|--------|
| **Akwam** | ✅ نشط | أفلام ومسلسلات |
| **Anime4up** | ✅ نشط | أنمي مدبلج |
| **AnimeBlkom** | ✅ نشط | أنمي مترجم |
| **Animeiat** | ✅ نشط | أنمي عربي |
| **ArabSeed** | ✅ نشط | أفلام عربية |
| **Cima4u** | ✅ نشط | سينما عربية |
| **CimaClub** | ✅ نشط | أفلام ومسلسلات |
| **CimaLeek** | ✅ نشط | محتوى عربي |
| **CimaNow** | ✅ نشط | أحدث الأفلام |
| **EgyBest** | ✅ نشط | أفلام مصرية |
| **FajerShow** | ✅ نشط | برامج تلفزيونية |
| **FaselHD** | ✅ نشط | محتوى عربي |
| **Fushaar** | ✅ نشط | أفلام عالمية |
| **MovizLands** | ✅ نشط | أفلام ومسلسلات |
| **Movizland** | ✅ نشط | محتوى متنوع |
| **MyCima** | ✅ نشط | سينما عربية |
| **Shahid4u** | ✅ نشط | مسلسلات عربية |
| **ShahidMBC** | ✅ نشط | محتوى MBC |
| **Shed4u** | ✅ نشط | أفلام عربية |
| **TopCinema** | ✅ نشط | سينما عربية |

## 🔧 التثبيت

### الطريقة السهلة (موصى بها)
1. افتح تطبيق CloudStream
2. اضغط على أيقونة الإعدادات ⚙️
3. انتقل إلى "المستودعات" أو "Repositories"
4. اضغط على زر "+" أو "إضافة"
5. الصق هذا الرابط:
```
https://raw.githubusercontent.com/dhomred/cloudstream-extensions-arabic/master/repo.json
```

⚠️ **روابط بديلة إذا واجهت مشاكل:**
- **الرئيسي (GitHub):** `https://raw.githubusercontent.com/dhomred/cloudstream-extensions-arabic/master/repo.json`
- **Codeberg (fallback):** `https://codeberg.org/dhomred/cloudstream-extensions-arabic/src/branch/master/repo.json`
- **تحميل مباشر:** `https://codeberg.org/dhomred/cloudstream-extensions-arabic/archive/master.zip`

### الطريقة اليدوية
1. حمل الملفات من قسم [Releases](https://codeberg.org/dhomred/cloudstream-extensions-arabic/releases)
2. انسخ الملفات إلى مجلد الملحقات
3. أعد تشغيل التطبيق

## 🏗️ البنية المجلدية

```
cloudstream-extensions-arabic-v2/
├── 📁 docs/                    # التوثيق
│   ├── README.md              # هذا الملف
│   ├── INSTALLATION.md        # دليل التثبيت
│   ├── EXTRACTORS.md          # توثيق المستخرجات
│   └── PROVIDERS.md           # توثيق المزودين
├── 📁 scripts/                # أدوات التطوير
│   ├── build.py              # بناء المشروع
│   ├── test.py               # اختبار الملحقات
│   └── analyze.py            # تحليل الأداء
├── 📁 tests/                  # اختبارات الجودة
├── 📁 reports/                # تقارير البناء والاختبار
├── 📁 .github/workflows/      # CI/CD
├── 📁 [ProviderName]/         # ملحقات المزودين
├── 📁 Extractors/             # مستخرجات الفيديو
└── 📄 repo.json               # ملف الإعدادات
```

## 🔍 استخدام المستخرجات

المستخرجات تدعم مواقع الفيديو التالية:

- **StreamTape** - مشغل فيديو سريع
- **DoodStream** - مشغل فيديو موثوق
- **MixDrop** - مشغل فيديو عالي الجودة
- **MegaUp** - مشغل فيديو متعدد الجودات
- **FileMoon** - مشغل فيديو متقدم

## 📊 الإحصائيات

- **عدد المزودين:** 20+ مزود
- **عدد المستخرجات:** 48+ مستخرج
- **اللغات المدعومة:** العربية، الإنجليزية
- **نسبة النجاح:** 95%+
- **آخر تحديث:** 2025

## 🛠️ التطوير

### المتطلبات
- Java 11+
- Kotlin 1.8+
- Gradle 7.0+
- Python 3.8+ (للأدوات المساعدة)

### البناء
```bash
# بناء جميع الملحقات
./gradlew build

# اختبار الملحقات
./gradlew test

# تحليل الجودة
python scripts/analyze.py
```

### إضافة مزود جديد
1. انسخ نموذج المزود من `TemplateProvider/`
2. عدل الإعدادات في `build.gradle.kts`
3. طبق معايير الجودة
4. أضف الاختبارات
5. حدث التوثيق

## 📝 المساهمة

نرحب بالمساهمات! يرجى اتباع الخطوات التالية:

1. **Fork** المشروع
2. أنشئ **branch** جديد: `git checkout -b feature/مزود-جديد`
3. ارتكب التغييرات: `git commit -m 'إضافة مزود جديد'`
4. ادفع التغييرات: `git push origin feature/مزود-جديد`
5. افتح **Pull Request**

### معايير المساهمة
- ✅ اتباع معايير Kotlin
- ✅ إضافة اختبارات جودة
- ✅ تحديث التوثيق
- ✅ اختبار المزود قبل الإرسال

## 🛠️ استكشاف الأخطاء وإصلاحها

### مشكلة "الصفحة غير موجودة" أو "404" عند إضافة المستودع:

#### ✅ الحلول المضمونة:
1. **استخدم الرابط الرئيسي (موصى به):**
   ```
   https://raw.githubusercontent.com/dhomred/cloudstream-extensions-arabic/master/repo.json
   ```

2. **إذا استمرت المشكلة، جرب التثبيت اليدوي:**
   - حمل الملف من [Releases](https://codeberg.org/dhomred/cloudstream-extensions-arabic/releases)
   - استخرج الملفات إلى مجلد `cloudstream/extensions/`

#### ❌ الروابط التي لا تعمل مع Codeberg:
- `https://codeberg.org/dhomred/cloudstream-extensions-arabic/raw/branch/master/repo.json` (غير مدعوم)
- `https://codeberg.org/dhomred/cloudstream-extensions-arabic/src/branch/master/repo.json` (يتطلب صلاحيات)

### المزودين لا يعملون
- تأكد من أنك متصل بالإنترنت
- جرب تفعيل/إيقاف المزود من الإعدادات
- تحقق من وجود تحديثات للتطبيق
- أبلغ عن المشكلة في قسم [Issues](https://codeberg.org/dhomred/cloudstream-extensions-arabic/issues)

## 📞 التواصل

- **الإبلاغ عن المشكلات:** [Issues](https://codeberg.org/dhomred/cloudstream-extensions-arabic/issues)
- **اقتراحات الميزات:** [Discussions](https://codeberg.org/dhomred/cloudstream-extensions-arabic/issues)
- **الدعم الفني:** قم بفتح issue جديد

## ⚖️ الترخيص

هذا المشروع مرخص تحت **MIT License**. انظر ملف [LICENSE](LICENSE) للتفاصيل.

---

<p align="center">
  ⭐ <strong>إذا أعجبك المشروع، لا تنسى أن تمنحنا نجمة!</strong> ⭐
</p>

<p align="center">
  <a href="https://codeberg.org/dhomred/cloudstream-extensions-arabic">
    <img src="https://img.shields.io/github/stars/dhomred/cloudstream-extensions-arabic?style=social" alt="Star">
  </a>
</p>
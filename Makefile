# 📋 Makefile - CloudStream Extensions Arabic
# هذا الملف يحتوي على أوامر مفيدة لتطوير المشروع

.PHONY: help build test clean lint validate stats backup sync install dev setup

# 🎯 الأوامر الأساسية
help: ## عرض قائمة الأوامر المتاحة
	@echo "🚀 CloudStream Extensions Arabic - أوامر التطوير"
	@echo "=========================================="
	@echo ""
	@echo "📋 الأوامر الأساسية:"
	@echo "  make help       - عرض هذه القائمة"
	@echo "  make setup      - إعداد المشروع للتطوير"
	@echo "  make build      - بناء المشروع"
	@echo "  make test       - تشغيل الاختبارات"
	@echo "  make clean      - تنظيف ملفات البناء"
	@echo "  make lint       - فحص جودة الكود"
	@echo "  make validate   - التحقق من الامتدادات"
	@echo "  make stats      - إحصائيات المشروع"
	@echo "  make backup     - إنشاء نسخة احتياطية"
	@echo "  make sync       - مزامنة الامتدادات"
	@echo "  make install    - تثبيت المتطلبات"
	@echo "  make dev        - بدء وضع التطوير"
	@echo ""
	@echo "📊 أوامر Git:"
	@echo "  make git-status - حالة Git"
	@echo "  make git-add    - إضافة التغييرات"
	@echo "  make git-commit - تنفيذ commit"
	@echo "  make git-push   - دفع التغييرات"
	@echo "  make git-pull   - سحب التحديثات"
	@echo ""
	@echo "🛠️ أوامر الصيانة:"
	@echo "  make update     - تحديث المشروع"
	@echo "  make optimize   - تحسين الأداء"
	@echo "  make security   - فحص الأمان"
	@echo "  make docs       - إنشاء التوثيق"
	@echo "  make release    - إصدار جديد"

# 🔧 إعداد المشروع
setup: ## إعداد المشروع للتطوير
	@echo "🔧 إعداد المشروع..."
	python -m pip install --upgrade pip
	pip install -r requirements.txt
	chmod +x gradlew
	./gradlew build
	@echo "✅ تم إعداد المشروع بنجاح"

# 🏗️ بناء المشروع
build: ## بناء المشروع
	@echo "🏗️ بناء المشروع..."
	./gradlew build
	python scripts/generate_stats.py
	@echo "✅ تم بناء المشروع بنجاح"

# 🧪 تشغيل الاختبارات
test: ## تشغيل الاختبارات
	@echo "🧪 تشغيل الاختبارات..."
	./gradlew test
	python scripts/validate_extensions.py
	@echo "✅ تم تشغيل الاختبارات بنجاح"

# 🧹 تنظيف المشروع
clean: ## تنظيف ملفات البناء
	@echo "🧹 تنظيف المشروع..."
	./gradlew clean
	find . -name "*.log" -delete
	find . -name "__pycache__" -type d -exec rm -rf {} +
	find . -name "*.pyc" -delete
	find . -name ".DS_Store" -delete
	@echo "✅ تم تنظيف المشروع بنجاح"

# 🔍 فحص جودة الكود
lint: ## فحص جودة الكود
	@echo "🔍 فحص جودة الكود..."
	python scripts/lint.py
	@echo "✅ تم فحص جودة الكود بنجاح"

# ✅ التحقق من الامتدادات
validate: ## التحقق من الامتدادات
	@echo "✅ التحقق من الامتدادات..."
	python scripts/validate_extensions.py
	@echo "✅ تم التحقق من الامتدادات بنجاح"

# 📊 إحصائيات المشروع
stats: ## إحصائيات المشروع
	@echo "📊 إنشاء إحصائيات المشروع..."
	python scripts/generate_stats.py
	@echo "✅ تم إنشاء الإحصائيات بنجاح"

# 💾 إنشاء نسخة احتياطية
backup: ## إنشاء نسخة احتياطية
	@echo "💾 إنشاء نسخة احتياطية..."
	python scripts/backup.py
	@echo "✅ تم إنشاء النسخة الاحتياطية بنجاح"

# 🔄 مزامنة الامتدادات
sync: ## مزامنة الامتدادات
	@echo "🔄 مزامنة الامتدادات..."
	python scripts/sync_extensions.py
	@echo "✅ تم مزامنة الامتدادات بنجاح"

# 📦 تثبيت المتطلبات
install: ## تثبيت المتطلبات
	@echo "📦 تثبيت المتطلبات..."
	python -m pip install --upgrade pip
	pip install -r requirements.txt
	@echo "✅ تم تثبيت المتطلبات بنجاح"

# 🚀 وضع التطوير
dev: ## بدء وضع التطوير
	@echo "🚀 بدء وضع التطوير..."
	python scripts/dev_mode.py
	@echo "✅ تم بدء وضع التطوير بنجاح"

# 📋 أوامر Git
git-status: ## حالة Git
	git status

git-add: ## إضافة التغييرات
	git add .

git-commit: ## تنفيذ commit
	@echo "📝 تنفيذ commit..."
	git add .
	git commit -m "$(message)"

git-push: ## دفع التغييرات
	@echo "📤 دفع التغييرات..."
	git push origin master

git-pull: ## سحب التحديثات
	@echo "📥 سحب التحديثات..."
	git pull origin master

# 🔧 أوامر الصيانة
update: ## تحديث المشروع
	@echo "🔧 تحديث المشروع..."
	git pull origin master
	pip install --upgrade -r requirements.txt
	./gradlew clean build
	@echo "✅ تم تحديث المشروع بنجاح"

optimize: ## تحسين الأداء
	@echo "⚡ تحسين الأداء..."
	python scripts/optimize.py
	@echo "✅ تم تحسين الأداء بنجاح"

security: ## فحص الأمان
	@echo "🔒 فحص الأمان..."
	python scripts/security_check.py
	@echo "✅ تم فحص الأمان بنجاح"

docs: ## إنشاء التوثيق
	@echo "📚 إنشاء التوثيق..."
	python scripts/generate_docs.py
	@echo "✅ تم إنشاء التوثيق بنجاح"

release: ## إصدار جديد
	@echo "🎯 إصدار جديد..."
	python scripts/release.py
	@echo "✅ تم إصدار الإصدار الجديد بنجاح"

# 🎉 أوامر ممتعة
fun: ## أوامر ممتعة
	@echo "🎉 CloudStream Extensions Arabic - أوامر ممتعة!"
	@echo ""
	@echo "🌟 هل تعلم؟"
	@echo "  - لدينا أكثر من 48 مستخرج فيديو!"
	@echo "  - ندعم أكثر من 20 مزود محتوى عربي!"
	@echo "  - نسبة نجاحنا تتجاوز 95%!"
	@echo ""
	@echo "🚀 أوامر ممتعة:"
	@echo "  make coffee     - ☕ احصل على قهوة!"
	@echo "  make joke       - 😂 نكتة برمجية!"
	@echo "  make quote      - 💬 اقتباس تحفيزي!"
	@echo "  make weather    - 🌤️ طقس اليوم!"

coffee: ## ☕ احصل على قهوة!
	@echo "☕ جاري تحضير قهوتك..."
	@echo "  ████████████████████"
	@echo "  ████☕☕☕☕☕☕☕☕████"
	@echo "  ████████████████████"
	@echo "  ☕ تم تحضير قهوتك! استمتع بالبرمجة!"

joke: ## 😂 نكتة برمجية!
	@echo "😂 نكتة برمجية:"
	@echo ""
	@echo "  لماذا غادر المبرمج حفلته؟"
	@echo "  لأنه لم يكن هناك exception!"
	@echo ""
	@echo "  (أو ربما كان هناك Stack Overflow!)"

quote: ## 💬 اقتباس تحفيزي!
	@echo "💬 اقتباس تحفيزي:"
	@echo ""
	@echo "  'البرمجة ليست فقط عن الكود، بل عن حل المشكلات'"
	@echo "  - مطور مجهول"
	@echo ""
	@echo "  'أفضل طريقة للتعلم هي الممارسة والخطأ'"
	@echo "  - مطور CloudStream"

weather: ## 🌤️ طقس اليوم!
	@echo "🌤️ طقس اليوم في عالم البرمجة:"
	@echo ""
	@echo "  🌡️  درجة الحرارة: مثالية للبرمجة"
	@echo "  💨 الرياح: نسيم خفيف من الأفكار الجديدة"
	@echo "  ☁️  الغيوم: سحب من الإبداع"
	@echo "  🌈 قوس قزح: ألوان متعددة من اللغات البرمجية"
	@echo ""
	@echo "  ⭐ تقييم اليوم: 10/10 للبرمجة!"
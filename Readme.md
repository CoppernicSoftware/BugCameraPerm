Bug in Camera Permission
========================

Process of this app
-------------------

Knowing that this app is a system APP (Signed with device's keys)

 1) Getting permission status for CAMERA
 2) Showing two button to get permission on two manners
     - Directly via Android API
     - Through Dexter API

Bug
---

**expected behavior**

After first install of this application, on the first run :

Android should grant CAMERA permission silently.

**actual behavior**

Android is showing permission pop up.

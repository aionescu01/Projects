import { initializeApp } from "firebase/app";
import { getAuth } from "firebase/auth";


const firebaseConfig = {
    apiKey: "AIzaSyBdsnSXzQwuQgQISfbvFfL6RoOurnFQMSA",
    authDomain: "csa-fc-341-c31f1.firebaseapp.com",
    projectId: "csa-fc-341-c31f1",
    storageBucket: "csa-fc-341-c31f1.appspot.com",
    messagingSenderId: "738164234064",
    appId: "1:738164234064:web:e7ac12b0e528957dcdbe55"
};

export const app = initializeApp(firebaseConfig);
export const auth = getAuth(app);
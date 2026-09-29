"use client";
import {FormEvent,useState} from "react";
import {useRouter} from "next/navigation";
const API=process.env.NEXT_PUBLIC_API_URL;

export default function StudentAccess(){
 const router=useRouter();
 const [mode,setMode]=useState<"create"|"login">("create");
 const [msg,setMsg]=useState("");
 const [busy,setBusy]=useState(false);

 async function createAccount(e:FormEvent<HTMLFormElement>){
  e.preventDefault(); setMsg("");
  const form=e.currentTarget, fd=new FormData(form);
  const password=String(fd.get("password")||"");
  const confirm=String(fd.get("confirmPassword")||"");
  const roll=String(fd.get("universityRollNo")||"").trim();
  if(password!==confirm){setMsg("Password and Confirm Password do not match.");return}
  if(!API){setMsg("Registration service is unavailable.");return}
  setBusy(true);
  const payload=new FormData();
  payload.set("collegeRollNo",roll);
  payload.set("universityRollNo",roll);
  payload.set("name",roll);
  payload.set("department","PENDING_VERIFICATION");
  payload.set("studyYear","PENDING_VERIFICATION");
  payload.set("requestedRole","STUDENT");
  payload.set("password",password);
  const card=fd.get("idCard");
  if(card instanceof File)payload.set("idCard",card);
  try{
   const r=await fetch(API+"/api/registration",{method:"POST",body:payload});
   const data=await r.json();
   if(!r.ok)throw new Error(data.error||"Registration failed.");
   setMsg("Registration submitted. Your User ID is your University Roll Number. After RKGITM ID verification, use it with your password to login.");
   form.reset();
  }catch(err){setMsg(err instanceof Error?err.message:"Registration failed.");}
  finally{setBusy(false)}
 }

 async function login(e:FormEvent<HTMLFormElement>){
  e.preventDefault(); setMsg("");
  if(!API){setMsg("Login service is unavailable.");return}
  setBusy(true);
  const fd=new FormData(e.currentTarget);
  try{
   const r=await fetch(API+"/api/auth/login",{method:"POST",headers:{"Content-Type":"application/json"},body:JSON.stringify({username:fd.get("username"),password:fd.get("password")})});
   if(!r.ok)throw new Error();
   const data=await r.json();
   if(data.role!=="STUDENT")throw new Error();
   sessionStorage.setItem("rkgitm_token",data.token);
   sessionStorage.setItem("rkgitm_role",data.role);
   sessionStorage.setItem("rkgitm_uid",data.uid||"");
   router.replace("/student");
  }catch{setMsg("Invalid Student User ID/password, or your RKGITM verification is still pending.");}
  finally{setBusy(false)}
 }

 return <main className="loginPage">
  <a href="/" className="backLink">← RKGITM Achievement Hub</a>
  <section className="loginCard">
   <div className="loginBrand"><span>RA</span><div><strong>Student Portal</strong><small>RKGITM Achievement Hub</small></div></div>
   <p className="eyebrow">STUDENT ACCESS</p>
   <h1>{mode==="create"?"Create your":"Welcome back to"}<br/><em>RKGITM account.</em></h1>
   <div className="studentAccessTabs">
    <button type="button" className={mode==="create"?"studentAccessTab active":"studentAccessTab"} onClick={()=>{setMode("create");setMsg("")}}>Create Account</button>
    <button type="button" className={mode==="login"?"studentAccessTab active":"studentAccessTab"} onClick={()=>{setMode("login");setMsg("")}}>Login</button>
   </div>
   {mode==="create"?<form onSubmit={createAccount}>
    <label>University Roll Number<input name="universityRollNo" required autoComplete="username" placeholder="Enter university roll number"/></label>
    <label>RKGITM College ID Card<input name="idCard" type="file" accept="image/*" required/></label>
    <small className="secureNote">Only an RKGITM college ID card is accepted. It will be checked before the account is activated. Maximum 5 MB.</small>
    <label>Password<input name="password" type="password" minLength={8} required autoComplete="new-password" placeholder="Minimum 8 characters"/></label>
    <label>Confirm Password<input name="confirmPassword" type="password" minLength={8} required autoComplete="new-password" placeholder="Re-enter password"/></label>
    <button disabled={busy}>{busy?"Submitting…":"Create Account →"}</button>
   </form>:<form onSubmit={login}>
    <label>User ID<input name="username" required autoComplete="username" placeholder="University Roll Number"/></label>
    <label>Password<input name="password" type="password" required autoComplete="current-password" placeholder="••••••••"/></label>
    <button disabled={busy}>{busy?"Signing in…":"Login →"}</button>
   </form>}
   {msg&&<p className="notice" role="status">{msg}</p>}
  </section>
 </main>
}
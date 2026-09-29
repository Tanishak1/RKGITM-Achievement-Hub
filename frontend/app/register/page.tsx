"use client";
import {FormEvent,useEffect,useState} from "react";
import {useSearchParams} from "next/navigation";
const API=process.env.NEXT_PUBLIC_API_URL;
export default function Register(){
 const params=useSearchParams();const [msg,setMsg]=useState(""),[busy,setBusy]=useState(false);
 useEffect(()=>{if(params.get("role")!=="FACULTY")location.replace("/student-access")},[params]);
 async function submit(e:FormEvent<HTMLFormElement>){e.preventDefault();setMsg("");const form=e.currentTarget,fd=new FormData(form);const password=String(fd.get("password")||""),confirm=String(fd.get("confirmPassword")||"");if(password!==confirm){setMsg("Password and Confirm Password do not match.");return}fd.delete("confirmPassword");fd.set("requestedRole","FACULTY");if(!API){setMsg("Registration service is unavailable.");return}setBusy(true);try{const r=await fetch(API+"/api/registration",{method:"POST",body:fd});const data=await r.json();if(!r.ok)throw new Error(data.error||"Registration failed");setMsg("Faculty request submitted. Super Admin approval ke baad aapka unique Faculty User ID generate hoga.");form.reset()}catch(err){setMsg(err instanceof Error?err.message:"Registration failed.")}finally{setBusy(false)}}
 return <main className="loginPage"><a href="/login" className="backLink">← Campus Login</a><section className="loginCard"><div className="loginBrand"><span>RA</span><div><strong>Faculty Portal</strong><small>RKGITM identity verification</small></div></div><p className="eyebrow">FACULTY REGISTRATION</p><h1>Create faculty<br/><em>campus access.</em></h1><p>Your RKGITM identity will be reviewed by the Super Admin before access is activated.</p><form onSubmit={submit}>
 <label>Faculty Name<input name="name" required placeholder="Full faculty name"/></label>
 <input type="hidden" name="collegeRollNo" value="AUTO"/>
 <label>Department<input name="department" required placeholder="e.g. CSE"/></label>
 <label>RKGITM Faculty ID Card<input name="idCard" type="file" accept="image/*" required/></label><small className="secureNote">Upload a clear RKGITM faculty ID card image. Maximum 5 MB.</small>
 <label>Password<input name="password" type="password" minLength={8} required autoComplete="new-password" placeholder="Minimum 8 characters"/></label><label>Confirm Password<input name="confirmPassword" type="password" minLength={8} required autoComplete="new-password" placeholder="Re-enter password"/></label>
 <button disabled={busy}>{busy?"Submitting…":"Create Faculty Account →"}</button>{msg&&<p className="notice" role="status">{msg}</p>}</form><a className="proof" href="/login">Already approved? Faculty Login →</a></section></main>
}
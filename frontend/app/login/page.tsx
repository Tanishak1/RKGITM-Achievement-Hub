"use client";
import {FormEvent,useState} from "react";
import {useRouter} from "next/navigation";
const API=process.env.NEXT_PUBLIC_API_URL;
export default function Login(){
 const router=useRouter();
 const [msg,setMsg]=useState("");
 const [busy,setBusy]=useState(false);
 async function submit(e:FormEvent<HTMLFormElement>){
  e.preventDefault();
  if(!API){setMsg("Authentication will be available when the backend is connected.");return;}
  setBusy(true);setMsg("");
  const fd=new FormData(e.currentTarget);
  try{
   const r=await fetch(API+"/api/auth/login",{method:"POST",headers:{"Content-Type":"application/json"},body:JSON.stringify({username:fd.get("username"),password:fd.get("password")})});
   if(!r.ok)throw new Error();
   const data=await r.json();
   sessionStorage.setItem("rkgitm_token",data.token);
   router.replace("/faculty");
  }catch{setMsg("Invalid credentials or authentication service unavailable.");}
  finally{setBusy(false);}
 }
 return (
  <main className="loginPage">
   <a href="/" className="backLink">← Public Achievement Hub</a>
   <section className="loginCard">
    <div className="loginBrand"><span>RA</span><div><strong>RKGITM Achievement Hub</strong><small>Authorized access</small></div></div>
    <p className="eyebrow">FACULTY &amp; ADMIN</p>
    <h1>Review with<br/><em>confidence.</em></h1>
    <p>Sign in to verify student submissions and manage institutional records.</p>
    <form onSubmit={submit}>
     <label>Username<input name="username" autoComplete="username" required placeholder="Institutional username"/></label>
     <label>Password<input name="password" type="password" autoComplete="current-password" required placeholder="••••••••"/></label>
     <button disabled={busy}>{busy?"Signing in…":"Secure sign in →"}</button>
     {msg&&<p className="notice" role="status">{msg}</p>}
    </form>
    <small className="secureNote">Protected faculty workspace • Session expires automatically</small>
   </section>
  </main>
 );
}
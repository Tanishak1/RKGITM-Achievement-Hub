"use client";
import {FormEvent,useState} from "react";
const API=process.env.NEXT_PUBLIC_API_URL;
export default function Submit(){
 const [message,setMessage]=useState(""); const [busy,setBusy]=useState(false);
 async function send(e:FormEvent<HTMLFormElement>){e.preventDefault();setBusy(true);setMessage("");
  const form=e.currentTarget; const fd=new FormData(form); const body=Object.fromEntries(fd.entries());
  if(!API){setMessage("Preview mode: form UI is ready. Backend deployment will enable live submissions.");setBusy(false);return;}
  try{const r=await fetch(API+"/api/achievements",{method:"POST",headers:{"Content-Type":"application/json",Authorization:"Bearer "+(sessionStorage.getItem("rkgitm_token")||"")},body:JSON.stringify(body)});
   if(!r.ok)throw new Error(); form.reset();setMessage("Submitted successfully. Your achievement is pending verification.");
  }catch{setMessage("Submission service is temporarily unavailable. Please try again later.");}finally{setBusy(false)}
 }
 return <main className="formPage"><a className="backLink" href="/">← Back to Achievement Hub</a><div className="formWrap">
 <p className="eyebrow">STUDENT ACHIEVEMENT PORTAL</p><h1>Put your work<br/><em>on the record.</em></h1>
 <p>Share an achievement with supporting evidence. Submitted records remain private until an authorized reviewer verifies them.</p>
 <form onSubmit={send}>
  <label>Student name<input name="studentName" required minLength={2} placeholder="Your full name"/></label>
  <label>Achievement title<input name="title" required minLength={3} placeholder="e.g. Finalist — National Hackathon"/></label>
  <div className="two"><label>Department<input name="department" required placeholder="e.g. CSE"/></label><label>Category<select name="category" required><option value="">Select category</option><option>Hackathon</option><option>Internship</option><option>Research</option><option>Competition</option><option>Certification</option><option>Community & Leadership</option><option>Other</option></select></label></div>
  <label>Description<textarea name="description" required minLength={20} rows={5} placeholder="Briefly explain the achievement, your role and outcome."/></label>
  <label>Evidence / proof URL<input name="proofUrl" type="url" placeholder="Certificate, official result, GitHub or portfolio URL"/></label>
  <div className="verificationNote"><strong>Verification first.</strong><span>Submission does not mean publication. Evidence is reviewed before a record appears publicly.</span></div>
  <button disabled={busy} type="submit">{busy?"Submitting…":"Submit for verification →"}</button>
  {message&&<p className="notice" role="status">{message}</p>}
 </form></div></main>
}
"use client";
import {FormEvent,useMemo,useState} from "react";
const API=process.env.NEXT_PUBLIC_API_URL;
export default function Submit(){
 const [message,setMessage]=useState("");const [busy,setBusy]=useState(false);
 const [studentName,setStudentName]=useState("");const [title,setTitle]=useState("");const [department,setDepartment]=useState("");const [program,setProgram]=useState("B.Tech");const [studyYear,setStudyYear]=useState("");const [description,setDescription]=useState("");const [photoUrl,setPhotoUrl]=useState("");
 const generated=useMemo(()=>{const who=studentName||"Student";const course=[program,department,studyYear].filter(Boolean).join(", ");const achievement=title||"an achievement";return `${who}${course?", "+course:""} has recently achieved ${achievement}. ${description||"Add a short description of the achievement, role and outcome to complete this post."}`},[studentName,program,department,studyYear,title,description]);
 async function send(e:FormEvent<HTMLFormElement>){e.preventDefault();setBusy(true);setMessage("");const form=e.currentTarget;const fd=new FormData(form);const body=Object.fromEntries(fd.entries());body.description=generated;
  if(!API){setMessage("Preview mode: form UI is ready. Backend deployment will enable live submissions.");setBusy(false);return}
  try{const r=await fetch(API+"/api/achievements",{method:"POST",headers:{"Content-Type":"application/json",Authorization:"Bearer "+(sessionStorage.getItem("rkgitm_token")||"")},body:JSON.stringify(body)});if(!r.ok)throw new Error();form.reset();setStudentName("");setTitle("");setDepartment("");setStudyYear("");setDescription("");setPhotoUrl("");setMessage("Submitted successfully. Your post is pending faculty verification.")}
  catch{setMessage("Submission service is temporarily unavailable. Please try again later.")}finally{setBusy(false)}
 }
 return <main className="formPage"><a className="backLink" href="/">← Back to Achievement Hub</a><div className="formWrap">
 <p className="eyebrow">STUDENT ACHIEVEMENT PORTAL</p><h1>Share your milestone<br/><em>with the campus.</em></h1><p>Create a professional achievement post. It remains private until faculty verification.</p>
 <div className="composerLayout"><form onSubmit={send}>
  <label>Student name<input name="studentName" value={studentName} onChange={e=>setStudentName(e.target.value)} required minLength={2} placeholder="Your full name"/></label>
  <label>Achievement title<input name="title" value={title} onChange={e=>setTitle(e.target.value)} required minLength={3} placeholder="e.g. Finalist — National Hackathon"/></label>
  <div className="two"><label>Program<select name="program" value={program} onChange={e=>setProgram(e.target.value)}><option>B.Tech</option><option>B.Arch</option><option>M.Tech</option><option>MBA</option><option>Other</option></select></label><label>Department<input name="department" value={department} onChange={e=>setDepartment(e.target.value)} required placeholder="e.g. CSE"/></label></div>
  <label>Study year<input name="studyYear" value={studyYear} onChange={e=>setStudyYear(e.target.value)} placeholder="e.g. 3rd Year"/></label>
  <label>Your achievement story<textarea value={description} onChange={e=>setDescription(e.target.value)} required minLength={20} rows={5} placeholder="What did you achieve? Mention your role, event/project and outcome."/></label>
  <label>Post photo / image URL<input name="photoUrl" type="url" value={photoUrl} onChange={e=>setPhotoUrl(e.target.value)} placeholder="Paste an image URL"/></label>
  <label>Evidence / proof URL<input name="proofUrl" type="url" placeholder="Certificate, official result, GitHub or portfolio URL"/></label>
  <input type="hidden" name="description" value={generated}/>
  <div className="verificationNote"><strong>Verification first.</strong><span>Your post becomes public only after an authorized faculty reviewer approves the evidence.</span></div>
  <button disabled={busy} type="submit">{busy?"Submitting…":"Submit post for verification →"}</button>{message&&<p className="notice" role="status">{message}</p>}
 </form>
 <aside className="postPreview"><p className="eyebrow">LIVE POST PREVIEW</p><div className="postAuthor"><span>{(studentName||"S").slice(0,1).toUpperCase()}</span><div><strong>{studentName||"Your name"}</strong><small>{[program,department,studyYear].filter(Boolean).join(" • ")||"Student profile"}</small></div></div>{photoUrl&&<img src={photoUrl} alt="Achievement preview"/>}<h2>{title||"Your achievement title"}</h2><p>{generated}</p><div className="postPreviewFoot"><span>RKGITM Achievement Hub</span><b>Pending verification</b></div></aside>
 </div></div></main>
}
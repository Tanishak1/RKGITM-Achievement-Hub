"use client";
import {useEffect,useState} from "react";
const API=process.env.NEXT_PUBLIC_API_URL;
type A={id:number;title:string;studentName:string;department?:string;category?:string;description?:string;proofUrl?:string;createdAt?:string;studentUid?:string;};
export default function Achievements(){
 const [items,setItems]=useState<A[]>([]),[loading,setLoading]=useState(true),[q,setQ]=useState("");
 useEffect(()=>{if(!API){setLoading(false);return}fetch(API+"/api/achievements/public").then(r=>r.ok?r.json():[]).then((data)=>setItems((data as A[]).filter((x:A)=>!["PROJECT","RESEARCH"].includes((x.category||"").toUpperCase()))).catch(()=>setItems([])).finally(()=>setLoading(false))},[]);
 const shown=items.filter(x=>q===""||(x.title+" "+x.studentName+" "+(x.description||"")).toLowerCase().includes(q.toLowerCase()));
 return <main className="directoryPage achievementFeed"><a className="backLink" href="/">← Achievement Hub</a><header className="directoryHero"><p className="eyebrow">VERIFIED CAMPUS STORIES</p><h1>Achievements worth<br/><em>celebrating.</em></h1><p>Approved student milestones, shared as stories rather than records.</p></header>
 <div className="achievementSearch"><input value={q} onChange={e=>setQ(e.target.value)} placeholder="Search student or achievement…"/></div>
 {loading?<div className="showcaseSkeleton"><i/><i/><i/></div>:shown.length?<section className="achievementPostGrid">{shown.map(a=><article className="achievementPost" key={a.id}>
  <header><div className="postAvatar">{(a.studentName||"S").slice(0,1).toUpperCase()}</div><div><strong>{a.studentName}</strong><small>{[a.department,a.createdAt?new Date(a.createdAt).toLocaleDateString():null].filter(Boolean).join(" · ")}</small></div><mark>Verified</mark></header>
  <div className="postBody"><h2>{a.title}</h2><p>{a.description}</p></div>
  <footer>{a.studentUid&&<a className="postAction" href={"/profile/"+encodeURIComponent(a.studentUid)}>View student profile <b>→</b></a>}{a.proofUrl&&<a className="postAction evidence" href={a.proofUrl} target="_blank" rel="noreferrer">View verified evidence <b>↗</b></a>}</footer>
 </article>)}</section>:<div className="contentState"><strong>No matching verified posts.</strong><p>Try another search.</p></div>}</main>
}
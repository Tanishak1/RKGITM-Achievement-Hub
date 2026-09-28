"use client";
import {useEffect,useState} from "react";
import {useRouter} from "next/navigation";
const API=process.env.NEXT_PUBLIC_API_URL;
type User={uid:string;name?:string;department?:string;active:boolean;createdAt?:string};
type Overview={registeredStudents:number;liveStudents:number;registeredFaculty:number;liveFaculty:number;pendingFaculty:number;pendingStudents:number;students:User[];faculty:User[]};
export default function Admin(){const router=useRouter();const [data,setData]=useState<Overview|null>(null);const [msg,setMsg]=useState("");const [tab,setTab]=useState<"students"|"faculty">("students");const [q,setQ]=useState("");const [mobileMenu,setMobileMenu]=useState(false);
 const token=()=>typeof window==="undefined"?"":sessionStorage.getItem("rkgitm_token")||"";
 async function load(){if(!API)return setMsg("Backend connection unavailable.");try{const r=await fetch(API+"/api/super-admin/overview",{headers:{Authorization:"Bearer "+token()}});if(r.status===401||r.status===403){router.replace("/login");return}if(!r.ok)throw new Error();setData(await r.json())}catch{setMsg("Could not load control center.")}}
 useEffect(()=>{if(!token())router.replace("/login");else void load()},[]);
 async function toggle(u:User){const r=await fetch(API+`/api/super-admin/users/${encodeURIComponent(u.uid)}/active?value=${!u.active}`,{method:"PATCH",headers:{Authorization:"Bearer "+token()}});if(r.ok)void load()}
 const users=(data?.[tab]||[]).filter(u=>(u.name+" "+u.uid+" "+u.department).toLowerCase().includes(q.toLowerCase()));
 return <main className="saShell"><aside className="saSide saSideV9">
  <div className="saBrandV9">
    <div className="saLogoV9">RA</div>
    <div className="saBrandTextV9"><strong>RKGITM</strong><span>Achievement Hub</span></div><button className="saMenuBtnV10" aria-label="Open admin menu" onClick={()=>setMobileMenu(v=>!v)}>{mobileMenu?"×":"☰"}</button>
  </div>

  <nav className={`saNavV9 ${mobileMenu?"mobileOpen":""}`}>
    <a className="saNavItemV9 active" href="/admin"><span className="saIconV9">⌂</span><span className="saLabelV9">Overview</span></a>
    <a className="saNavItemV9" href="#accounts" onClick={()=>setTab("students")}><span className="saIconV9">♙</span><span className="saLabelV9">Students</span></a>
    <a className="saNavItemV9" href="#accounts" onClick={()=>setTab("faculty")}><span className="saIconV9">♙</span><span className="saLabelV9">Faculty</span></a>
    <a className="saNavItemV9" href="/admin/identities"><span className="saIconV9">✓</span><span className="saLabelV9">Approvals</span><span className="saBadgeV9">{data?.pendingFaculty||0}</span></a>
    <a className="saNavItemV9" href="/achievements"><span className="saIconV9">◇</span><span className="saLabelV9">Achievements</span></a>
    <a className="saNavItemV9" href="/events"><span className="saIconV9">▣</span><span className="saLabelV9">Events &amp; Activities</span></a>
    <a className="saNavItemV9" href="/admin/content"><span className="saIconV9">◉</span><span className="saLabelV9">Communities</span></a>
    <a className="saNavItemV9" href="/admin/content"><span className="saIconV9">⌁</span><span className="saLabelV9">Analytics</span></a>
    <a className="saNavItemV9" href="/admin/content"><span className="saIconV9">▤</span><span className="saLabelV9">Reports</span></a>
    <a className="saNavItemV9" href="/admin/content"><span className="saIconV9">⚙</span><span className="saLabelV9">Settings</span></a>
  </nav>

  <div className={`saBottomV9 ${mobileMenu?"mobileOpen":""}`}>
    <div className="saInfoCardV9">
      <div className="saInfoIconV9">🏆</div>
      <strong>Building a Recognized Campus</strong>
      <span>Students • Faculty • Projects</span>
      <span>Research • Communities</span>
    </div>
    <div className="saProfileV9">
      <span className="saAvatarV9">SA</span>
      <div><strong>Super Admin</strong><span>Full access</span></div>
    </div>
  </div>
</aside>
 <section className="saMain"><header className="saHeader"><div><p>ADMINISTRATION / OVERVIEW</p><h1>Control Center</h1><span>Institutional access, approvals and account health in one place.</span></div><div><a href="/">View public site ↗</a><button onClick={()=>{sessionStorage.clear();router.replace("/login")}}>Sign out</button></div></header>
 {msg&&<div className="saAlert">{msg}</div>}
 <div className="saKpis"><article><span>Students</span><strong>{data?.registeredStudents??"—"}</strong><small><b>{data?.liveStudents??0}</b> active accounts</small></article><article><span>Faculty</span><strong>{data?.registeredFaculty??"—"}</strong><small><b>{data?.liveFaculty??0}</b> active accounts</small></article><article className={(data?.pendingFaculty||0)>0?"attention":""}><span>Faculty approvals</span><strong>{data?.pendingFaculty??"—"}</strong><a href="/admin/identities">Review requests →</a></article><article className={(data?.pendingStudents||0)>0?"attention":""}><span>Student approvals</span><strong>{data?.pendingStudents??"—"}</strong><small>Handled by faculty</small></article></div>
 <div className="saGrid"><section id="accounts" className="saPanel saAccounts"><div className="saPanelHead"><div><p>ACCOUNT DIRECTORY</p><h2>Campus accounts</h2></div><div className="saTabs"><button className={tab==="students"?"on":""} onClick={()=>setTab("students")}>Students</button><button className={tab==="faculty"?"on":""} onClick={()=>setTab("faculty")}>Faculty</button></div></div><div className="saSearch"><span>⌕</span><input value={q} onChange={e=>setQ(e.target.value)} placeholder="Search name, UID or department"/><em>{users.length} accounts</em></div>
 <div className="saTable"><div className="saTr head"><span>Person</span><span>Department</span><span>Status</span><span>Access</span></div>{users.map(u=><div className="saTr" key={u.uid}><span><b>{(u.name||u.uid).slice(0,1).toUpperCase()}</b><i><strong>{u.name||"Unnamed user"}</strong><small>{u.uid}</small></i></span><span>{u.department||"—"}</span><span><mark className={u.active?"live":"off"}>{u.active?"● Active":"○ Inactive"}</mark></span><span><button onClick={()=>void toggle(u)}>{u.active?"Deactivate":"Activate"}</button></span></div>)}</div>{users.length===0&&<div className="saEmpty">No {tab} accounts yet.</div>}</section>
 <aside className="saPanel saQuick"><p>QUICK ACTIONS</p><h2>Needs attention</h2><a href="/admin/identities"><span>Faculty identity requests</span><strong>{data?.pendingFaculty||0}</strong><small>Review institutional access →</small></a><a href="/faculty"><span>Student approvals</span><strong>{data?.pendingStudents||0}</strong><small>Open faculty workflow →</small></a><a href="/admin/content"><span>Content workspace</span><b>Manage</b><small>Projects, research & events →</small></a></aside></div></section></main>}
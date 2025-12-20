const quizzesListEl = document.getElementById('quizzes-list');
const quizView = document.getElementById('quiz-view');
const quizzesView = document.getElementById('quizzes-view');
const quizTitleEl = document.getElementById('quiz-title');
const quizForm = document.getElementById('quiz-form');
const quizResult = document.getElementById('quiz-result');
const backBtn = document.getElementById('back-btn');

let currentQuizFullTitle = null;
let currentQuizId = null;
let currentQuizTitle = null;
let currentQuestions = [];

function showQuizzesView(){
  quizView.classList.add('hidden');
  quizzesView.classList.remove('hidden');
}

function showQuizView(){
  quizzesView.classList.add('hidden');
  quizView.classList.remove('hidden');
}

async function loadQuizzes(){
  quizzesListEl.textContent = 'Loading quizzes...';
  try{
    const res = await fetch('/quiz/all');
    const data = await res.json();
    const list = data.data || [];
    renderQuizzes(list);
  }catch(err){
    quizzesListEl.textContent = 'Failed to load quizzes.';
  }
}

function renderQuizzes(list){
  quizzesListEl.innerHTML = '';
  if(list.length===0){
    quizzesListEl.textContent = 'No quizzes available.';
    return;
  }
  list.forEach(q=>{
    const card = document.createElement('div');
    card.className = 'quiz-card';
    const title = document.createElement('div');
    title.textContent = `${q.title} (${q.numberOfQuestions} Qs)`;
    const startBtn = document.createElement('button');
    startBtn.textContent = 'Start';
    startBtn.onclick = ()=>startQuiz(q.title);
    card.appendChild(title);
    card.appendChild(startBtn);
    quizzesListEl.appendChild(card);
  });
}

async function startQuiz(fullTitle){
  currentQuizFullTitle = fullTitle;
  const parts = fullTitle.split(' - ');
  currentQuizId = parts.length>1? parts[parts.length-1].trim(): null;
  currentQuizTitle= parts[0];
  quizTitleEl.textContent = `Quiz: ${fullTitle}`;
  quizResult.classList.add('hidden');
  quizForm.innerHTML = 'Loading...';
  showQuizView();
  try{
    const res = await fetch(`/quiz/get/${currentQuizTitle}`);
    const json = await res.json();
    const questions = json.data || [];
    currentQuestions = questions;
    renderQuestions(questions);
  }catch(err){
    quizForm.innerHTML = '<div>Failed to load quiz questions.</div>';
  }
}

function renderQuestions(questions){
  quizForm.innerHTML = '';
  if(questions.length===0){
    quizForm.innerHTML = '<div>No questions found for this quiz.</div>';
    return;
  }
  questions.forEach((q, idx)=>{
    const qDiv = document.createElement('div');
    qDiv.className = 'question';
    const qText = document.createElement('div');
    qText.textContent = (idx+1) + '. ' + q.question;
    qDiv.appendChild(qText);
    const opts = document.createElement('div');
    opts.className = 'options';

    ['option1','option2','option3','option4'].forEach(optKey=>{
      if(!q[optKey]) return;
      const id = `q_${q.id}_${optKey}`;
      const label = document.createElement('label');
      label.style.display = 'block';
      const input = document.createElement('input');
      input.type = 'radio';
      input.name = `q_${q.id}`;
      input.value = q[optKey];
      input.id = id;
      label.appendChild(input);
      const span = document.createElement('span');
      span.textContent = ' ' + q[optKey];
      label.appendChild(span);
      opts.appendChild(label);
    });

    qDiv.appendChild(opts);
    quizForm.appendChild(qDiv);
  });

  const submitBtn = document.createElement('button');
  submitBtn.textContent = 'Submit';
  submitBtn.onclick = async (e)=>{
    e.preventDefault();
    await submitQuiz();
  };
  quizForm.appendChild(submitBtn);
}

async function submitQuiz(){
  if(!currentQuizId){
    quizResult.textContent = 'Cannot determine quiz id for submission.';
    quizResult.classList.remove('hidden');
    return;
  }
  const responses = [];
  currentQuestions.forEach(q=>{
    const els = document.getElementsByName(`q_${q.id}`);
    let selected = null;
    for(const el of els){
      if(el.checked){ selected = el.value; break; }
    }
    responses.push({id: q.id, response: selected || ''});
  });
  try{
    const res = await fetch(`/quiz/submit/${currentQuizId}`,{
      method: 'POST',
      headers:{'Content-Type':'application/json'},
      body: JSON.stringify(responses)
    });
    const json = await res.json();
    quizResult.textContent = json.data || 'Result not returned';
    quizResult.classList.remove('hidden');
  }catch(err){
    quizResult.textContent = 'Failed to submit quiz.';
    quizResult.classList.remove('hidden');
  }
}

backBtn.onclick = ()=>{
  showQuizzesView();
};

// initial load
loadQuizzes();
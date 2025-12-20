const addQuestionForm = document.getElementById('add-question-form');
const addQuestionStatus = document.getElementById('add-question-status');
const createQuizForm = document.getElementById('create-quiz-form');
const createQuizStatus = document.getElementById('create-quiz-status');

if(addQuestionForm){
  addQuestionForm.addEventListener('submit', async (e)=>{
    e.preventDefault();
    addQuestionStatus.textContent = 'Saving...';
    const form = e.target;
    const payload = {
      question: form.question.value,
      option1: form.option1.value,
      option2: form.option2.value,
      option3: form.option3.value || null,
      option4: form.option4.value || null,
      answer: form.answer.value,
      category: form.category.value,
      difficultyLevel: form.difficultyLevel.value
    };
    try{
      const res = await fetch('/question/add',{
        method:'POST',
        headers: {'Content-Type':'application/json'},
        body: JSON.stringify(payload)
      });
      const json = await res.json();
      addQuestionStatus.textContent = json.message || json.data || 'Question added';
      form.reset();
    }catch(err){
      addQuestionStatus.textContent = 'Failed to add question';
    }
    setTimeout(()=>addQuestionStatus.textContent=' ',3000);
  });
}

if(createQuizForm){
  createQuizForm.addEventListener('submit', async (e)=>{
    e.preventDefault();
    createQuizStatus.textContent = 'Creating...';
    const form = e.target;
    const payload = {
      title: form.title.value,
      category: form.category.value,
      difficultyLevel: form.difficultyLevel.value,
      numberOfQuestions: parseInt(form.numberOfQuestions.value,10)
    };
    try{
      const res = await fetch('/quiz/create',{
        method:'POST',
        headers: {'Content-Type':'application/json'},
        body: JSON.stringify(payload)
      });
      const json = await res.json();
      createQuizStatus.textContent = json.message || json.data || 'Quiz created';
      form.reset();
    }catch(err){
      createQuizStatus.textContent = 'Failed to create quiz';
    }
    setTimeout(()=>createQuizStatus.textContent=' ',3000);
  });
}
